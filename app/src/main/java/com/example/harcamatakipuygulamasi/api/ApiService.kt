package com.example.harcamatakipuygulamasi.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val API_URL = "https://v6.exchangerate-api.com/v6/16e543f554b38993c501efb0/latest/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit
    .Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(API_URL).build()

interface ApiService
{
    @GET("TRY")
    fun getUpdateRates() : Call<ApiResponse>
}

object Api { val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) } }