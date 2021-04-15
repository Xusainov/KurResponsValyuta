package com.example.kursresponsvalyuta.splash

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.navOptions
import com.example.kursresponsvalyuta.MainActivity

data class ProgressAnimation(
    var context: Context,
    var progressBar: ProgressBar,
//    var textView: TextView,
    var from: Float,
    var to: Float
) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        val value = from + (to - from) * interpolatedTime
        progressBar.setProgress(value.toInt())
//        textView.setText(value.toString()+"%")


        if (value == to) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

        }

    }

}