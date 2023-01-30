package com.equitel.pruebaequitel.api

import com.equitel.pruebaequitel.Almacenamiento
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoogleCloudService {
    @POST("1")
    fun postEnvioPdf(@Body almacenamiento: Almacenamiento): Call<ResponseBody>
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://us-central1-bot-front.cloudfunctions.net/movilpdf/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
internal object CloudFunctions{
    val retrofitService: GoogleCloudService by lazy {
        retrofit.create(GoogleCloudService::class.java)
    }
}