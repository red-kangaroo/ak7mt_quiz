package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()

        val startButton = findViewById<ImageButton>(R.id.startButton)
        startButton.visibility = View.GONE
        startButton.setOnClickListener {
            startGame()
        }

        val loader = findViewById<ProgressBar>(R.id.introload)
        Handler().postDelayed({
            loader.visibility = View.GONE
            startGame()
//            startButton.visibility = View.VISIBLE
        }, 3000)
    }

    private fun startGame() {
        val moveTo = Intent(this, Stats::class.java)
        startActivity(moveTo)
        finish()
    }
}
