package com.e.moal_admin


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.dialog_set_part.*
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var partList =arrayListOf<JobInfo>()
        var job : JobTimeInfo = JobTimeInfo()

        btn_part_add.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogView = layoutInflater.inflate(R.layout.dialog_set_part, null, false)
            val dialogText = dialogView.findViewById<EditText>(R.id.dialog_part_name)
            val dialogStartHourPicker: NumberPicker = dialogView.findViewById(R.id.dialog_start_hour_picker)
            val dialogStartMinPicker: NumberPicker = dialogView.findViewById(R.id.dialog_start_min_picker)
            val dialogEndHourPicker: NumberPicker = dialogView.findViewById(R.id.dialog_end_hour_picker)
            val dialogEndMinPicker: NumberPicker = dialogView.findViewById(R.id.dialog_end_min_picker)

            dialogStartHourPicker.minValue = 0
            dialogStartHourPicker.maxValue = 24
            dialogEndHourPicker.minValue = 0
            dialogEndHourPicker.maxValue = 24

            dialogStartMinPicker.minValue = 0
            dialogStartMinPicker.maxValue = 59
            dialogEndMinPicker.minValue = 0
            dialogEndMinPicker.maxValue = 59

            dialogStartHourPicker.wrapSelectorWheel = true
            dialogStartHourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogEndHourPicker.wrapSelectorWheel = true
            dialogEndHourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogStartMinPicker.wrapSelectorWheel = true
            dialogStartMinPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogEndMinPicker.wrapSelectorWheel = true
            dialogEndMinPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지

            val pickedEndHour = dialogEndHourPicker.value
            val pickedEndMin = dialogEndMinPicker.value
            val pickedStartHour = dialogStartHourPicker.value
            val pickedStartMin = dialogStartMinPicker.value

            builder.setView(dialogView)
                .setPositiveButton("완료"){ dialogInterface, i ->
                    job.startHour=pickedStartHour
                    job.startMin=pickedStartMin
                    job.endHour=pickedEndHour
                    job.endMin=pickedEndMin
                }
                .setNegativeButton("취소"){ dialogInterface, i -> }
                .show()
        }

    }
}
