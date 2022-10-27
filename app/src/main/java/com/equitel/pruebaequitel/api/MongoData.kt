package com.equitel.pruebaequitel.api

import com.equitel.pruebaequitel.Almacenamiento
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface MongoData {
    @POST("mongodb")
    suspend fun postMongodb(@Body almacenamiento: Almacenamiento): String

}

private var mongoRetrofit = Retrofit.Builder()