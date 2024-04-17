package com.example.imagecaching.api

import com.example.imagecaching.apiDataClass.ApiDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api/v2/content/misc/media-coverages")
    suspend fun getPosts(@Query("limit") limit: Int):Response<ApiDataClass>
}