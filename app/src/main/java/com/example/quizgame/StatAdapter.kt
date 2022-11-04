package com.example.quizgame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

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

        return mainRow
    }

}
