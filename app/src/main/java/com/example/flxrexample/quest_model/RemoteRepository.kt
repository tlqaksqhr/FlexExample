package com.example.flxrexample.quest_model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteRepository {
    @Multipart
    @POST("/upload")
    fun uploadImage(@Part file: MultipartBody.Part): Call<String>

    @POST("/result")
    fun getAuthResult(@Body authData: ImageAuthData) : Call<ImageAuthResult>
}