package com.achsanit.newsnippet.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> resourceMapper(code: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(code.invoke())
    } catch (httpException: HttpException) {
        when (httpException.code()) {
            in 400..499 -> {
                Resource.Error(
                    httpException.message.toString() + "-${httpException.code()}",
                    httpException.code()
                )
            }

            in 500..599 -> {
                Resource.Error(
                    httpException.message.toString() + "-${httpException.code()}",
                    httpException.code()
                )
            }

            else -> {
                Resource.Error(
                    httpException.message.toString() + "-${httpException.code()}",
                    httpException.code()
                )
            }
        }
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        Resource.Error(e.message.toString(), CodeError.NO_INTERNET_CONNECTION)
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        Resource.Error(e.message.toString(), CodeError.REQUEST_TIME_OUT)
    } catch (e: Exception) {
        Resource.Error(e.message.toString(), CodeError.SOMETHING_WENT_WRONG)
    }
}