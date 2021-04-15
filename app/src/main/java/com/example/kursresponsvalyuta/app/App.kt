package com.example.kursresponsvalyuta.app

import android.app.Application
import com.example.kursresponsvalyuta.network.ApiCliant

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        ApiCliant.init()
    }
}