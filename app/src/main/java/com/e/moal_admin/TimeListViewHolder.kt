package com.e.moal_admin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_time_interval.view.*

class TimeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
    fun bindTimeView(timeModel : TimeIntervalModel){
        itemView.timeNumbers.text=timeModel.time.toString()
        //itemView.apm.text=timeModel.apm
    }
}