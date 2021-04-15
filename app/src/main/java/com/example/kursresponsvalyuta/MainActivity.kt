package com.example.kursresponsvalyuta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.Bottom_Menu)
        navController = findNavController(R.id.frag_oyna)

        bottomNavigationView.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{

            when (it.itemId) {

                R.id.page_1 -> {
                    navController.popBackStack()
                    navController.navigate(R.id.firthFragment)
                    navController.popBackStack()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.page_2 -> {
                    navController.popBackStack()
                    navController.navigate(R.id.calendarFragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }


    }
}
