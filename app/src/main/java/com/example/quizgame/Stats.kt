package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Toast
import java.io.*

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

        val startButton = findViewById<ImageButton>(R.id.startButton)
        startButton.setOnClickListener {
            val intent = Intent(this, Questions::class.java)
            startActivity(intent)
        }

        val statusOverlay: ConstraintLayout = findViewById(R.id.statListOverlay)
        val statPupUp = findViewById<ListView>(R.id.statPopUp)
        statusOverlay.visibility = View.GONE

        val optionMenu = findViewById<ImageView>(R.id.optionMenu)
        optionMenu.setOnClickListener {
            statusOverlay.visibility = View.VISIBLE
            val saveName = "QuizGameSaveScore"

            try {
                var fileInputStream: FileInputStream = openFileInput(saveName)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val scores: ArrayList<String> = arrayListOf()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    text?.let { tx -> scores.add(tx) }
                }
                for(s in scores){
                    statPupUp.adapter = AnswerAdapter(this, scores)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this,"Could not retrieve player scores.", Toast.LENGTH_LONG).show()
            }
        }

        val optionPlayer = findViewById<ImageView>(R.id.optionPlayer)
        optionPlayer.setOnClickListener {
            statusOverlay.visibility = View.VISIBLE
            statPupUp.adapter = StatNameAdapter(this)
        }
    }
}
