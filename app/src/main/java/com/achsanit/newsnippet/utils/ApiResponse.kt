package com.achsanit.newsnippet.utils

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String, val codeError: Int) : ApiResponse<Nothing>()
    data class ServerError(val errorMessage: String, val codeError: Int = 500) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}