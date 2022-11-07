package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageButton
import com.example.quizgame.StatFeed

class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val allStats: ArrayList<StatFeed> = ArrayList()
        allStats.add(StatFeed(name = "Total Score", image = R.drawable.icon_trophy))
        allStats.add(StatFeed(name = "Test", image = R.drawable.icon_note))
        allStats.add(StatFeed(name = "Previous Score", image = R.drawable.icon_calendar))
        allStats.add(StatFeed(name = "Time Taken", image = R.drawable.icon_clock))

        val grid: GridView = findViewById<GridView>(R.id.statusGridView)
        grid.adapter = StatAdapter(this, allStats)

        val nxtButton = findViewById<ImageButton>(R.id.nextButton)
        nxtButton.setOnClickListener {
            val intent = Intent(this, XX::class.java)  // TODO
            startActivity(intent)
        }
    }
}
