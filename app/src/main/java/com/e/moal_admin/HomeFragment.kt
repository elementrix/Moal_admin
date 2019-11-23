package com.e.moal_admin


import android.app.Activity
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.view.get
import com.google.firebase.database.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var rootRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    var dayChildrenCount : Long = 0 // dayChildrenCount의 경우 DB에서 읽어온 자료가 저장되는 자료인데 onViewCreated 이후에도 살아남아야 하므로 밖에다가 저장
    var partChildrenCount : ArrayList<Long> = ArrayList() // 나중에 listView를 꾸리기 위한 큰그림 변수
    var positionNames :String = "" // 마찬가지
    var partNames : String = "" // 마찬가지2

    val dirFire: DatabaseReference = rootRef.child("노랑통닭 홍대점") // **수정필요**

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
        val positionHeader : MutableList<String> = ArrayList() // 주방, 서빙 등 자리 이름
        val partHeader : MutableList<String> = ArrayList() // 오픈, 마감 등 파트이름

        val positionBody : MutableList<MutableList<MutableList<String>>> = ArrayList()

        calendar.setOnDateChangedListener(OnDateSelectedListener() { calendar: MaterialCalendarView, calendarDay: CalendarDay, b: Boolean ->
            positionHeader.clear()
            partHeader.clear()
            positionBody.clear() //firebase에서 받아온 자료들이 있는 곳인데 일단 먼저 clear해주고 담아야 함

            val selectedDay :CalendarDay = calendar.selectedDate
            val year = selectedDay.year // 2019
            val unOrderedDate = selectedDay.date.toString() //Wed Nov 20 00:00:00 GMT+09:00 2019
            val orderedDate : List<String> = unOrderedDate.split("\\s".toRegex()) //[Wed, Nov] // 필요한 부분만 파싱후 공백을 기준으로 스플릿 함
            var day = orderedDate[0]
            val month_date = orderedDate[1] // Nov
            val month_num = selectedDay.month+1 // 11

            var day_kor : String = "월" //월,화,수,목,금,토,일 (일단 월요일로 초기화 함 초기화를 안하면 못 불러옴.)
            when(day){
                "Mon" -> { day_kor = "월" }
                "Tue" -> { day_kor = "화" }
                "Wed" -> { day_kor = "수" }
                "Thr" -> { day_kor = "목" }
                "Fri" -> { day_kor = "금" }
                "Sat" -> { day_kor = "토" }
                "Sun" -> { day_kor = "일" }
            }

//            var list: ListView = dailyWorkersListView
//            var listItems : MutableList<Any> = ArrayList()

            dirFire.child("WorkingPart").child(day_kor).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    dayChildrenCount = p0.childrenCount
                    for (x in p0.children){ // 서빙, 주방 등등 목록을 읽어오고자 하는 부분
                        val partBody : MutableList<MutableList<String>>  = ArrayList() // 어차피 다른 날을 클릭하면 바뀔애들이니 더 상위레벨에서 정의할 필요 없음, 또한 매번 새로 만들어서 넣어줘야 하는 소모적인 녀석이니 여기에서 선언

                        positionNames = p0.child(x.key.toString()).key.toString()
                        Log.d("jooan","position is : "+positionNames) // 주방, 서빙 등등

                        positionHeader.add(positionNames) // 목록구성중 제일 윗단계

                        /*--------------------DB 읽기에는 전혀 관련없는 부분. 디버깅 시 건들지 말 것 (여기부터)---------------------------*/
                        val partChildrenCounting : Long = p0.child(x.key.toString()).childrenCount
                        partChildrenCount.add(partChildrenCounting)
                        /*--------------------DB 읽기에는 전혀 관련없는 부분. 디버깅 시 건들지 말 것 (여기까지)---------------------------*/

                        for (y in x.children){ // 오픈, 마감 등을 표시하고, 그 안에 들어가서 request목록을 읽어오고자 하는 부분

                            val eachPartWorkerList: MutableList<String> = ArrayList() // 실제로 일하는 사람들 목록이 담김, 매번 새로 만들어야 가리키는 요소가 달라짐
                            partNames = y.key.toString()
                            Log.d("jooan","part is :"+partNames) // 마감, 오픈 마감 등등
                            partHeader.add(partNames)

                            for(z in y.child("RequestList").children){ // request 목록에 있는 사람들 읽어옴

                                eachPartWorkerList.add(z.key.toString())
                            }
                           // partBodyTmp.add(eachPartWorkerList)

                            partBody.add(eachPartWorkerList) // 특정 파트(오픝,미들)에서 일하는 사람들의 목록을 넣어줌

//                            Log.d("jooan","the working people is :" + eachPartWorkerList) // 일하는 사람들의 목록
//                            Log.d("jooan","Now the partBody is :" + partBody) // 일하는 사람들의 목록
                            /*아직 if 문으로 accepted 안 걸러줌*/
                        }
                        positionBody.add(partBody) // 특정 포지션(서빙,주방)에서 일하는 사람들의 목록을 넣어줌
                        Log.d("jooan","the final form of part body is : "+partBody+", "+partHeader)
                    }
                    Log.d("jooan","the final form of position body is : "+positionBody+", "+positionHeader)
                    /*여기서 list view를 채웁니다.*/

                    var tmpCount = 0 // 마감, 오픈 몇개씩 있는지
                    for( i in  0 until positionHeader.size){ // 주방, 서빙
                        val tmpList : MutableList<String> =  ArrayList()
                        val expandableListView : ExpandableListView = dailyWorkersListView

                        Log.d("jooan","now the partChildrenCount is : "+partChildrenCount)
                        for(j in 0 until partChildrenCount[i]){ // 마감, 오픈
                            tmpList.add(partHeader[tmpCount])
                            tmpCount+=1
                        }
                        Log.d("jooan","now the tmpList is : "+tmpList)
                        expandableListView.setAdapter(ExpendableListAdapter(context,tmpList,positionBody[i]))

//                        if(expandableListView!=null) {
//                            listItems.add(expandableListView)
//                        }
                    }
//                    var arrayAdapter : ArrayAdapter<Any> = ArrayAdapter(context!!,R.layout.fragment_home,listItems) // 일단 돌려볼려고 이리저리 하는 것이지만,,, !!는 조심할 것
//                    list.adapter = arrayAdapter
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

        })
    }


    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
