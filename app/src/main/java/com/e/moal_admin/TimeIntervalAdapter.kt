package com.e.moal_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TimeIntervalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfTime = arrayListOf<TimeIntervalModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimeListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_time_interval, parent, false))
    }

    override fun getItemCount(): Int =listOfTime.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val timeViewHolder = viewHolder as TimeListViewHolder
        timeViewHolder.bindTimeView(listOfTime[position])
    }
    fun setTimeList(listOfTime: ArrayList<TimeIntervalModel>){
        this.listOfTime = listOfTime
        notifyDataSetChanged()
    }//??????????????????????????????????????


}