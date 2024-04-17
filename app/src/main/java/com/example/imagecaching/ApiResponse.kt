package com.example.imagecaching

//Generic class to define the loading state from the API

sealed class ApiResponse<out T>{
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String) : ApiResponse<T>()
    object Loading : ApiResponse<Nothing>()
}
