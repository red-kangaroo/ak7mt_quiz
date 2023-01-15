package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout

class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        supportActionBar?.hide()

        val allStats: ArrayList<StatFeed> = ArrayList()
        allStats.add(StatFeed(name = "Trophies", image = R.drawable.icon_trophy))
        allStats.add(StatFeed(name = "Categories", image = R.drawable.icon_note))
        allStats.add(StatFeed(name = "Previous Score", image = R.drawable.icon_calendar))
        allStats.add(StatFeed(name = "Time Taken", image = R.drawable.icon_clock))

        val grid = findViewById<GridView>(R.id.statusGridView)
        grid.adapter = StatAdapter(this, allStats)
        val loadBar = findViewById<ProgressBar>(R.id.questionLoad)
        loadBar.visibility = View.GONE

        val statusOverlay: ConstraintLayout = findViewById(R.id.statListOverlay)
        val statPupUp = findViewById<ListView>(R.id.statPopUp)
        statusOverlay.visibility = View.GONE

        val startButton = findViewById<ImageButton>(R.id.startButton)
        startButton.setOnClickListener {
            loadBar.visibility = View.VISIBLE
            statusOverlay.visibility = View.VISIBLE
            statPupUp.visibility = View.GONE
            val intent = Intent(this, Questions::class.java)
            startActivity(intent)
            finish()
        }

        val optionMenu = findViewById<ImageView>(R.id.optionMenu)
        optionMenu.setOnClickListener {
            statusOverlay.visibility = View.VISIBLE
            statPupUp.adapter = StatScoreAdapter(this)
        }

        val optionPlayer = findViewById<ImageView>(R.id.optionPlayer)
        optionPlayer.setOnClickListener {
            statusOverlay.visibility = View.VISIBLE
            statPupUp.adapter = StatNameAdapter(this)
        }
    }
}
