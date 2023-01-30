package com.equitel.pruebaequitel.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EquitelApiService {
    @GET("AplicacionMovil")
    suspend fun getTotal(@Query("orden") ordenar : String): String
    @GET("AplicacionMovil")
    suspend fun getPrueba(@Query("orden") ordenar : String): String
    @GET("Tecnicos")
    suspend fun getContraseña(@Query("Usuario") usuario : String) : String
    @GET("Movil_Serial_Motor")
    suspend fun getSerialMotor(): String
    @GET("Movil_Cliente")
    suspend fun getcliente() : String
    @GET("Movil_Marca_Generador")
    suspend fun getMarcaGenerador() : String
    @GET("Movil_Marca_Planta")
    suspend fun getMarcaPlanta() : String
    @GET("Movil_Modelo_Generador")
    suspend fun getModeloGenerador() : String
    @GET("Movil_Modelo_Motor")
    suspend fun getModeloMotor() : String
    @GET("Movil_Modelo_Planta")
    suspend fun getModeloPlanta() : String
    @GET("Movil_Serial_Generador")
    suspend fun getSerialGenerador() : String
    @GET("Movil_Serial_Planta")
    suspend fun getSerialPlanta() : String
    @GET("Movil_Tecnicos")
    suspend fun getTecnico() : String
    @GET("MovilSede")
    suspend fun getSede() : String


}

private var retrofit = Retrofit.Builder()
    .baseUrl("http://labroidesapi-test.us-east-2.elasticbeanstalk.com/api/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

var service: EquitelApiService = retrofit.create(EquitelApiService::class.java)