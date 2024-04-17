package com.example.imagecaching

sealed class ApiResponse<out T>{
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String) : ApiResponse<T>()
    object Loading : ApiResponse<Nothing>()
}
