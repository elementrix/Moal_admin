package com.e.moal_admin

import android.graphics.Color
import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_day.view.*
import com.e.moal_admin.DayScheduleModel
import kotlinx.android.synthetic.main.item_time_interval.view.*
import kotlinx.android.synthetic.main.part_time_cardview.view.*


class DayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(dayModel : DayScheduleModel) {
        //if(dayModel.Name != null && dayModel.fill ==null) {

        // }

        if (dayModel.fill != null ) {
            itemView.gridName.text = dayModel.fill
            itemView.gridName.setTextColor(Color.rgb(117, 117, 117))
            itemView.gridName.setTextSize(13F)
        }
        else{
            itemView.gridName.text = dayModel.Name
            itemView.gridName.setTextColor(Color.BLACK)
            itemView.gridName.setTextSize(13F)
        }
        //itemView.gridName.text
        //itemView.gridfill.text=dayModel.fill
        itemView.setBackgroundColor(dayModel.color)

       /* itemView.Layout.setOnClickListener {

        }*/
    }

}