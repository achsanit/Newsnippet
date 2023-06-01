package com.achsanit.newsnippet.data.network

import com.achsanit.newsnippet.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class CallApiAdapter<T> {

    private var result: Flow<ApiResponse<T>> =
        flow {
            try {
                val response = createCall()
                emit(ApiResponse.Success(response))
            } catch (e: HttpException)  {
                when(e.code()) {
                    in 400..499 -> {
                        emit(
                            ApiResponse.Error("${e.message.toString() +e.code()} ", e.code())
                        )
                    }
                    in 500..599 -> {
                        ApiResponse.ServerError("${e.message.toString() +e.code()} ", e.code())
                    }
                    else -> {
                        emit(
                            ApiResponse.Error("${e.message.toString() +e.code()} ", e.code())
                        )
                    }
                }
            } catch (e: UnknownHostException) {
                emit(
                    ApiResponse.Error(e.message.toString(), 1)
                )
                e.printStackTrace()
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error(e.message.toString(), 0))
                e.printStackTrace()
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString(), -1))
            }
        }.flowOn(Dispatchers.IO)

    abstract suspend fun createCall(): T

    fun asFlow(): Flow<ApiResponse<T>> = result
}