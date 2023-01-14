package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.io.*

class DoneAdapter(
    private val context: Context,
    private val info: DoneFeed
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
        val saveName = "QuizGameSave"
        var playerName: String = "Unknown"
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val doneBox: View = layoutInflater.inflate(R.layout.donelist, viewGroup, false)

        try {
            var fileInputStream: FileInputStream = context.openFileInput(saveName + "Name")
            var inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            playerName = stringBuilder.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context,"Could not retrieve player name.", Toast.LENGTH_LONG).show()
        }

        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(saveName + "Score", Context.MODE_PRIVATE)
            val scoreEntry: String = "${playerName}: ${info.answersOK.toString()}/${info.questionNumber.toString()}"
            fileOutputStream.write(scoreEntry.toByteArray())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        doneBox.findViewById<TextView>(R.id.score).text = "${playerName}'s Score"
        doneBox.findViewById<TextView>(R.id.correct).text = "Correct: ${info.answersOK.toString()}"
        doneBox.findViewById<TextView>(R.id.incorrect).text = "Incorrect: ${info.answersNOK.toString()}"

        val restartButton = doneBox.findViewById<ImageButton>(R.id.restart)
        restartButton.setOnClickListener {
            val moveTo = Intent(context, Stats::class.java)
            context.startActivity(moveTo)
        }

        return doneBox
    }
}
