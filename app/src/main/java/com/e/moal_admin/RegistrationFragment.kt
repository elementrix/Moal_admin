package com.e.moal_admin


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var timeList =arrayListOf<JobTimeInfo>()

        //파트타임 추가 버튼 눌렀을 때
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
            //0-24시로 만들어 줄거니까 최소 최대값 설정

            dialogStartMinPicker.minValue = 0
            dialogStartMinPicker.maxValue = 59
            dialogEndMinPicker.minValue = 0
            dialogEndMinPicker.maxValue = 59
            //0-59분으로 만들어 줄거니까 최소 최대값 설정

            dialogStartHourPicker.wrapSelectorWheel = true //무한루프 가능
            dialogStartHourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogEndHourPicker.wrapSelectorWheel = true //무한루프 가능
            dialogEndHourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogStartMinPicker.wrapSelectorWheel = true //무한루프 가능
            dialogStartMinPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지
            dialogEndMinPicker.wrapSelectorWheel = true //무한루프 가능
            dialogEndMinPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS //키보드로 수정하기 금지

            dialogStartHourPicker.setFormatter { i -> String.format("%02d",i) }
            dialogStartMinPicker.setFormatter { i -> String.format("%02d",i) }
            dialogEndHourPicker.setFormatter { i -> String.format("%02d",i) }
            dialogEndMinPicker.setFormatter { i -> String.format("%02d",i) }
            //각각의 다이얼 시간을 00, 01, ... , 24 이런식으로 보이게 만들어 줌

            var pickedEndHour = dialogEndHourPicker.value
            var pickedEndMin = dialogEndMinPicker.value
            var pickedStartHour = dialogStartHourPicker.value
            var pickedStartMin = dialogStartMinPicker.value


            dialogEndHourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                pickedEndHour = dialogEndHourPicker.value
            }
            dialogEndMinPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                pickedEndMin = dialogEndMinPicker.value
            }
            dialogStartHourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                pickedStartHour = dialogStartHourPicker.value
            }
            dialogStartMinPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                pickedStartMin = dialogStartMinPicker.value
            }//스크롤이 바뀔때마다 업데이트 해줄 수 있도록 리스너 등록


            //찍은 시간을 저장하는 변수들 안바뀌니까 val

            builder.setView(dialogView)
            val dialog : AlertDialog = builder.create()
            dialog.show()

            /*
            기본 nagative, positive button을 사용하지 않기 위한 노력1단계: dialog.dismiss()를 사용할 것이기 때문에 빌더자체를 보여주는게 아니라
            다이얼로그로 한번 옮겨와서 dismiss 함
            */

            val dialogBtnComplete : TextView = dialogView.findViewById(R.id.dialog_complete)
            val dialogBtnCancel : TextView = dialogView.findViewById(R.id.dialog_cancel)//확인,취소 버튼

            dialogBtnComplete.setOnClickListener{
                var jobTime = JobTimeInfo()
                jobTime.partName = dialogText.getText().toString()
                jobTime.startHour=pickedStartHour
                jobTime.startMin=pickedStartMin
                jobTime.endHour=pickedEndHour
                jobTime.endMin=pickedEndMin
                timeList.add(jobTime)

                part_time_list.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = TimeCardAdapter(timeList)
                }

                Log.d("good",timeList[0].partName+"'s start,end time is: "+timeList[0].startHour+ ","+timeList[0].endHour)

                dialog.dismiss()
            }

            dialogBtnCancel.setOnClickListener{
                dialog.dismiss()
            }
        }

    }
    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun writeNewJob (userId: String, address: String, name: String){
        val storeInfo = StoreInfo(address,name)
        //이 노랑통닭 홍대점은 나중에 로그인 기능 생기면 유저가 가진 가게를 찾아가서 정보를 업데이트 하도록 할거임
        database.child("노랑통닭 홍대점").child("WorkingPart").setValue(storeInfo)
    }
}
