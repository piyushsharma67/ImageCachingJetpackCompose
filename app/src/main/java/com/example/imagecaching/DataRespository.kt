package com.example.imagecaching

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.imagecaching.api.Api
import com.example.imagecaching.apiDataClass.ApiDataClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val apiService:Api) {

    private val _data=MutableStateFlow<ApiResponse<ApiDataClass?>>(ApiResponse.Loading)
    val data:StateFlow<ApiResponse<ApiDataClass?>> =_data

    companion object{
        const val LIMIT=100
    }

    //function which returns the data from the API

    suspend fun getImages() {

       try{
           val apiData= apiService.getPosts(LIMIT)
           if (apiData.isSuccessful) {
               _data.value=ApiResponse.Success(apiData.body())
           } else {
               // Handle error, maybe log it or return null
              throw Exception("Error occurred fetching data")
           }
       }catch (error:IOException){
           Log.d("Errror is",error.message.toString())
           _data.value=ApiResponse.Error(error.message.toString())
       }
    }
}
