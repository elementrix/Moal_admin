package com.e.moal_admin


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.day_calendar.*
import kotlinx.android.synthetic.main.day_calendar.day_sche_calendar
import kotlinx.android.synthetic.main.day_calendar.view.*
import kotlinx.android.synthetic.main.fragment_notification.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    val requestclicked  = 1
    var selectedstore = ""
    var rootRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val dirFire: DatabaseReference = rootRef

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = ArrayList<Int>()

        colors.add(Color.rgb(250, 190, 190))//c1
        colors.add(Color.rgb(248, 237, 170))//c2
        colors.add(Color.rgb(162,194,106))//c3
        colors.add(Color.rgb(166, 235, 142))//c4
        colors.add(Color.rgb(125, 211, 240))//하늜색
        //colors.add(Color.rgb(99, 135, 245))
        colors.add(Color.rgb(173, 211, 255))//c5
        colors.add(Color.rgb(106, 196, 185))//c9
        colors.add(Color.rgb(215, 190, 252))//c7
        colors.add(Color.rgb(211, 181, 228))//c8
        colors.add(Color.rgb(172, 165, 165))//c9

        initView()
        val listOfDay = ArrayList<DayScheduleModel>(generateDummyData())
        var timeList =arrayListOf<JobTimeForReading>()
        var jobTimes = arrayListOf<JobTimeForReading>()
//        val timecardAdapter = TimeCardAdapter(timeList)
        var storeList = arrayListOf<JobInfoForReading>()

        val postListener = object : ValueEventListener {

            val dayListAdapter = DayListAdapter()


            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
//                if (intent.hasExtra("clickedstore")){
//                    selectedstore = intent.getStringExtra("clickedstore")
//                }
//                else{
//                    selectedstore = "노랑통닭 홍대점"
//                }

                selectedstore = "노랑통닭 홍대점"
                storeList.clear()

                for (snapShotStore: DataSnapshot in p0.children) {
                    val storename = snapShotStore.key
                    if (storename == null) {

                    } else {
                        val jobInfoForReading = JobInfoForReading(storename)
                        storeList.add(jobInfoForReading)
                    }
                }
                store_list.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = StoreCardAdapter(storeList)
                }
                timeList.clear()
                val name = selectedstore
                for (snapShotDays: DataSnapshot in p0.child(selectedstore).child("WorkingPart").children) { //요일 // 위의 intent에서  null처리 했기때문에 selectedstore는 non-null
                    for (snapShotWorkingParts: DataSnapshot in snapShotDays.children) { //서빙
                        for (snapShotTime: DataSnapshot in snapShotWorkingParts.children) { //오미마
                            val day = snapShotDays.key
                            val position = snapShotWorkingParts.key
                            val part = snapShotTime.key
                            val jobTimeInfo: JobTimeInfo? =
                                snapShotTime.getValue(JobTimeInfo::class.java)

                            if (jobTimeInfo == null || day == null || position == null || part == null) {

                            } else {
                                val jobTimeForReading = JobTimeForReading(
                                    jobTimeInfo.startHour,
                                    jobTimeInfo.startMin,
                                    jobTimeInfo.endHour,
                                    jobTimeInfo.endMin,
                                    jobTimeInfo.requirePeopleNum,
                                    name,
                                    position,
                                    part,
                                    day

                                )
                                timeList.add(jobTimeForReading)
                            }
                        }
                    }
                }
                var i :Int =0

                for (jobTimeForReading in timeList) {
                    //jobtimeforreading 객체들이 들어있는 jobtimes에서 하나씩 읽기
                    val day = jobTimeForReading.jobDay
                    var dayInt = 0
                    when (day) {
                        "월" -> dayInt = 0
                        "화" -> dayInt = 1
                        "수" -> dayInt = 2
                        "목" -> dayInt = 3
                        "금" -> dayInt = 4
                        "토" -> dayInt = 5
                        "일" -> dayInt = 6
                    }
                    val positionName: String = jobTimeForReading.positionName
                    val partName: String = jobTimeForReading.partName
                    val startHour = jobTimeForReading.startHour
                    val startMin = (((jobTimeForReading.startMin)/60)*0.1).toFloat()
                    val endHour = jobTimeForReading.endHour
                    val endMin = (((jobTimeForReading.endMin)/60)*0.1).toFloat()
                    val timeInt: Float = 0.5F
                    var start: Float = (startHour + startMin).toFloat()
                    var st : Float = start
                    val end: Float = endHour + endMin
                    val viewnum: Int = 2 * (end - start).toInt()
                    var t: Int = 0 //listofDay 인덱스 변수
                    while (start < end) {

                        t = dayInt + (7 * 2 * start).toInt()
                        //dayModel = DayScheduleModel()
                        if((start*2) == (st+end-1)) {
                            listOfDay[t] = DayScheduleModel(positionName,null, colors[i])
                        }
                        else if((start*2) == st+end){
                            listOfDay[t] = DayScheduleModel(null,partName, colors[i])
                        }
                        else{
                            listOfDay[t] = DayScheduleModel(null,null, colors[i])
                        }
                        start = (start + timeInt)
                    }

                    if(i == 8){
                        i=0
                    }
                    else{
                        i++
                    }

                }
                day_sche_calendar.apply{
                    val dayListAdapter = DayListAdapter()
                    day_sche_calendar.adapter = dayListAdapter
                    dayListAdapter.setDayList(listOfDay)
                }
                time_list.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TimeCardAdapter2(timeList)
                    // jobTimes =timecardAdapter.copy()
                }



            }
        }
        dirFire.addValueEventListener(postListener)
    }

    fun initView() {


        time_sche_calendar.layoutManager = GridLayoutManager(context, 1)
        time_sche_calendar.addItemDecoration(GridItemDecoration(0, 2))
        /*time_sche_calendar.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.HORIZONTAL
            )
        )*/
        /*time_sche_calendar.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    LinearLayoutManager.VERTICAL
                )
            )*/

        day_sche_calendar.layoutManager = GridLayoutManager(context, 7)
        day_sche_calendar.addItemDecoration(GridItemDecoration(0, 2))
        /*day_sche_calendar.addItemDecoration(
            DividerItemDecoration(
            activity,
            LinearLayoutManager.HORIZONTAL
        ))*/

        /*day_sche_calendar.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )*/



        val timeListAdapter = TimeIntervalAdapter()
        time_sche_calendar.adapter = timeListAdapter
        timeListAdapter.setTimeList(timeSettingData())

        val dayListAdapter = DayListAdapter()
        day_sche_calendar.adapter = dayListAdapter
        dayListAdapter.setDayList(generateDummyData())



        val scheduleView = layoutInflater.inflate(R.layout.day_calendar,null,false)
        val daycal: RecyclerView = scheduleView.day_sche_calendar
        val timecal: RecyclerView = scheduleView.time_sche_calendar
        val scrollListener1: RecyclerView.OnScrollListener
        lateinit var scrollListener2: RecyclerView.OnScrollListener
        scrollListener1 = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                daycal.removeOnScrollListener(scrollListener2)
                daycal.scrollBy(dx, dy)
                daycal.addOnScrollListener(scrollListener2)
            }
        }

        scrollListener2 = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                timecal.removeOnScrollListener(scrollListener1)
                timecal.scrollBy(dx, dy)
                timecal.addOnScrollListener(scrollListener1)

            }
        }
        timecal.addOnScrollListener(scrollListener1)
        daycal.addOnScrollListener(scrollListener2)


    }

    private fun timeSettingData(): ArrayList<TimeIntervalModel> {
        var i: Int = 0
        val listOfTime = ArrayList<TimeIntervalModel>()
        var timeModel: TimeIntervalModel

        while (i < 24) {
            timeModel = TimeIntervalModel(i)
            listOfTime.add(timeModel)
            i++
        }
        return listOfTime
    }

    private fun generateDummyData(): ArrayList<DayScheduleModel> {
        val listOfDay = ArrayList<DayScheduleModel>()
        var i: Int = 0

//        val listOfTime =ArrayList<TimeIntervalModel>()
//        var timeModel : TimeIntervalModel
        var dayModel: DayScheduleModel

        while (i < 7 * 48) {
            dayModel = DayScheduleModel()
            listOfDay.add(dayModel)
            i++
        }

        return listOfDay
    }


}
