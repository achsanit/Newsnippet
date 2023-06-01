package com.achsanit.newsnippet.di

import androidx.room.Room
import com.achsanit.newsnippet.BuildConfig
import com.achsanit.newsnippet.data.local.NewsnippetDb
import com.achsanit.newsnippet.data.network.NewsService
import com.achsanit.newsnippet.utils.CustomInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { CustomInterceptor() }
    single {
        val customInterceptor: CustomInterceptor = get()
        val client = OkHttpClient.Builder()
            .addInterceptor(customInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }

        client.build()
    }
}

val apiModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(NewsService::class.java)
    }
}

val localDbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsnippetDb::class.java,
            "newsnippet.db"
        ).build()
    }
    factory{ get<NewsnippetDb>().newsnippetDao() }
}