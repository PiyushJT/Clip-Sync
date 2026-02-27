package com.piyushjt.clipsync.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ClipService {

    @POST("/")
    suspend fun exchange(@Body request: ResponseRequest) : Response<ResponseRequest>

}

data class ResponseRequest(
    val text: String
)

object RetrofitClient {


    private const val BASE_URL = "http://10.24.201.25:9876"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val clipService: ClipService by lazy {
        retrofit.create(ClipService::class.java)
    }


}