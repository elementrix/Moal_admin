package com.e.moal_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.part_time_cardciew3.view.*

class PositionListAdapter(val personList:MutableList<PositionApplyPerson>): RecyclerView.Adapter<PositionListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: PositionListAdapter.ViewHolder, position: Int) {
        val data: PositionApplyPerson = personList[position]
        holder.bind(data)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.part_time_cardciew3, parent, false)) {
        fun bind(data: PositionApplyPerson) {
            itemView.cardView_position.setText(data.positionName)
            itemView.cardView_partName.setText(data.partName)
        }
    }
}