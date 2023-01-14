package com.example.quizgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ScoreAdapter(
    private val context: Context,
    private val answers: MutableList<String>
) : BaseAdapter() {
    override fun getCount(): Int {
        return answers.count()
    }

    override fun getItem(p0: Int): Any {
        return p0.toLong()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val answerRow: View = layoutInflater.inflate(R.layout.answerlist, viewGroup, false)

        answerRow.findViewById<TextView>(R.id.answer_num).text = "${p0 + 1}."
        answerRow.findViewById<TextView>(R.id.answer_text).text = answers[p0]

        return answerRow
    }
}
