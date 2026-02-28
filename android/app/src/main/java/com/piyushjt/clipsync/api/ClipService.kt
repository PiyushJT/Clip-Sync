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
    val text: String?
)

object RetrofitClient {

    private fun createRetrofit(baseUrl: String): Retrofit {
        val url = "http://${baseUrl}:9876"
        
        return Retrofit.Builder()
            .baseUrl("$url/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClipService(baseUrl: String): ClipService {
        return createRetrofit(baseUrl).create(ClipService::class.java)
    }
}