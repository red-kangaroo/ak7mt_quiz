package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.io.*

class StatNameAdapter(private val context: Context
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
        val saveName = "QuizGameSaveData"
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val doneBox: View = layoutInflater.inflate(R.layout.namelist, viewGroup, false)
        val playerNameEdit = doneBox.findViewById<EditText>(R.id.playerName)

        try {
            var fileInputStream: FileInputStream = context.openFileInput(saveName)
            var inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            playerNameEdit.setText(stringBuilder.toString()).toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context,"Could not retrieve player name.",Toast.LENGTH_LONG).show()
        }

        val submitButton = doneBox.findViewById<ImageButton>(R.id.submit)
        submitButton.setOnClickListener {
            val playerName = playerNameEdit.text.toString()
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = context.openFileOutput(saveName, Context.MODE_PRIVATE)
                fileOutputStream.write(playerName.toByteArray())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

            Toast.makeText(context,"Player name saved.",Toast.LENGTH_LONG).show()
            val moveTo = Intent(context, Stats::class.java)
            context.startActivity(moveTo)
        }

        val restartButton = doneBox.findViewById<ImageButton>(R.id.restart)
        restartButton.setOnClickListener {
            val moveTo = Intent(context, Stats::class.java)
            context.startActivity(moveTo)
        }

        return doneBox
    }
}
