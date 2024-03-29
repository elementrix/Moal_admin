package com.e.moal_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.part_time_cardview.view.*

class TimeCardAdapter(val timeList:ArrayList<JobTimeInfo>): RecyclerView.Adapter<TimeCardAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeCardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun onBindViewHolder(holder: TimeCardAdapter.ViewHolder, position: Int) {
        val data: JobTimeInfo = timeList[position]
        holder.bind(data)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.part_time_cardview, parent, false)) {
        fun bind(data: JobTimeInfo) {
            itemView.cardView_startTime.text =  String.format("%02d",data.startHour)+" : "+String.format("%02d",data.startMin)
            itemView.cardView_endTime.text = String.format("%02d",data.endHour)+" : "+String.format("%02d",data.endMin)
            itemView.partName.text = data.partName
            itemView.cardView_people.text = data.requirePeopleNum.toString()+" 명"
        }
    }
}