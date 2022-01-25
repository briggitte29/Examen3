package com.example.myapplication.Network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val RUTA="https://dog.ceo/api/breed/hound/images/"

val  retrofit=Retrofit.Builder()
    .baseUrl(RUTA)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface ApiService {

    @GET("random")
    fun getMascota():Call<Mascota>

}



