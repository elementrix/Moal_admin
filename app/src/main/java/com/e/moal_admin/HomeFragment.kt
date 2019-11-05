package com.e.moal_admin


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
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

        val calendarView: io.blackbox_vision.materialcalendarview.view.CalendarView? = calendar_view

        calendarView?.shouldAnimateOnEnter(true)
            ?.setFirstDayOfWeek(Calendar.MONDAY)
            ?.setOnDateClickListener(activity.onDateClick)
            ?.setOnMonthChangeListener(this.onMonthChange)
            ?.setOnDateLongClickListener(this.onDateLongClick)
            ?.setOnMonthTitleClickListener(this.onMonthTitleClick)

        calendarView?.update(Calendar.getInstance(Locale.getDefault()))
    }


}
