package com.example.kursresponsvalyuta.splash


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kursresponsvalyuta.MainActivity
import com.example.kursresponsvalyuta.R
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.ChasingDots
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.RotatingCircle


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)


    val progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
    val doubleBounce: Sprite = Circle()
    progressBar.indeterminateDrawable = doubleBounce

    val deration: Thread = object : Thread() {
        override fun run() {
            try {
                sleep(2000)

            } catch (e: InterruptedException) {
                Log.i("Error or Splash", e.toString())
            } finally {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    deration.start()

}

}