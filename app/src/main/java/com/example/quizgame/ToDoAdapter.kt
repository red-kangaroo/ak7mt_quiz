package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class ToDoAdapter(private val context: Context
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
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val doneBox: View = layoutInflater.inflate(R.layout.todolist, viewGroup, false)

        val restartButton = doneBox.findViewById<ImageButton>(R.id.restart)
        restartButton.setOnClickListener {
            val moveTo = Intent(context, Stats::class.java)
            context.startActivity(moveTo)
        }

        return doneBox
    }
}
