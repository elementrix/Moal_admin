package com.e.moal_admin


import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.view.get
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar : MaterialCalendarView = calendarView
        val text : TextView = dailyWorkersListView

        calendar.setOnDateChangedListener(OnDateSelectedListener() { calendar: MaterialCalendarView, calendarDay: CalendarDay, b: Boolean ->
            val selectedDay :CalendarDay = calendar.selectedDate
            val year = selectedDay.year // 2019
            val unOrderedDate = selectedDay.date.toString() //Wed Nov 20 00:00:00 GMT+09:00 2019
            val orderedDate : List<String> = unOrderedDate.split("\\s".toRegex()) //[Wed, Nov]
            var day = orderedDate[0]
            val month_date = orderedDate[1] // Nov
            val month_num = selectedDay.month+1 // 11

            text.setText(year.toString()+" "+month_num+"월 "+orderedDate[0])
        })
    }

    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
