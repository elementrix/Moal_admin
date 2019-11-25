package com.e.moal_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.day_calendar.*
import kotlinx.android.synthetic.main.item_day.*

class DayListAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfDayList = arrayListOf<DayScheduleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DayListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false))
    }

    override fun getItemCount(): Int =listOfDayList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val dayViewHolder = viewHolder as DayListViewHolder
        dayViewHolder.bindView(listOfDayList[position])

            var color :Int = listOfDayList[position].color
            dayViewHolder.itemView.setBackgroundColor(color)
    }
    fun setDayList(listOfDayList: ArrayList<DayScheduleModel>){
        this.listOfDayList = listOfDayList
        notifyDataSetChanged()
    }





}