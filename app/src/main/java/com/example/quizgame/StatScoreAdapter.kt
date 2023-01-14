package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader

class StatScoreAdapter(private val context: Context
): BaseAdapter() {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(p0: Int): Any {
        return p0.toLong()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, view: View?, viewGroup: ViewGroup?): View {
        val saveName = "QuizGameSaveScore"
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val doneBox: View = layoutInflater.inflate(R.layout.scorelist, viewGroup, false)
        val scoreBox = doneBox.findViewById<ListView>(R.id.scoreBox)

        try {
            var fileInputStream: FileInputStream = context.openFileInput(saveName)
            var inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val scores: MutableList<String> = ArrayList()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                text?.let { tx ->
                    scores.add(tx)
                    Toast.makeText(context, tx, Toast.LENGTH_LONG).show()
//                    val lines = tx.split("10")
//                    for (l in lines) {
//                        scores.add(l)
//                    }
                }
            }
            // TODO: Problem is here:
            for(s in scores){
                scoreBox.adapter = ScoreAdapter(context, scores)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context,"No player scores yet.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context,"Could not retrieve player scores.", Toast.LENGTH_LONG).show()
        }

        val restartButton = doneBox.findViewById<ImageButton>(R.id.restart)
        restartButton.setOnClickListener {
            val moveTo = Intent(context, Stats::class.java)
            context.startActivity(moveTo)
        }

        return doneBox
    }
}
