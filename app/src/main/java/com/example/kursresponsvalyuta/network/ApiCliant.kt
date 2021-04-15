package com.example.kursresponsvalyuta.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiCliant {

    companion object {

        var retrofit: Retrofit? = null

        fun init() {
            retrofit = Retrofit.Builder()
                .baseUrl("https://cbu.uz/oz/arkhiv-kursov-valyut/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}