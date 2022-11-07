package com.example.quizgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class StatAdapter(
    private val context: Context,
    private val allStats: ArrayList<StatFeed>
): BaseAdapter() {
    override fun getCount(): Int {
        return allStats.count()
    }

    override fun getItem(p0: Int): Any {
        return p0.toLong()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, view: View?, viewGroup: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val mainRow: View = layoutInflater.inflate(R.layout.itemlist, viewGroup, false)

        var statText: TextView = mainRow.findViewById(R.id.stat_text)
        statText.text = allStats[p0].name
        var statImage: ImageView = mainRow.findViewById(R.id.stat_image)
        statImage.setImageResource(allStats[p0].image)

        return mainRow
    }

}
