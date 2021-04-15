package com.example.kursresponsvalyuta.network

import com.example.kursresponsvalyuta.model.ValyutaRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {


    @GET("json/")
    fun getAllData(): Call<ValyutaRespons>


    @GET("json/all/{data}/")
    fun getAllDavlatListData(@Path("data") data: String): Call<ValyutaRespons>


    @GET("json/{rub}/{data}/")
    fun getHomeRubOneDay(@Path("rub") rub: String, @Path("data") data: String): Call<ValyutaRespons>
    // json/all/2019-02-21/


}