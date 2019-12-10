package com.e.moal_admin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_registration.*


/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    var rootRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val dirFire: DatabaseReference = rootRef.child("stores").child("노랑통닭 홍대점") // **수정필요**
    var personLIst : MutableList<PositionApplyPerson> = ArrayList()
    var positionList : MutableList<PositionApplyPerson> = ArrayList()
    val positionPartNameSet : MutableList<MutableList<String>> = ArrayList() // [[목, 서빙, 마감],[목, 서빙, 오픈],...]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dirFire.child("WorkingPart").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                positionPartNameSet.clear()//데이터 바뀌면 한번 싹 갈아주고

                for(x in p0.children){ //요일들
                    val innerPostionPartNameSet : MutableList<String> = ArrayList() // 저기 들어가는 속쟤료들
                    for(y in x.children){ //포지션들
                        for(z in y.children){ // 파트들
                            innerPostionPartNameSet.add(x.key!!) //목 담기 사실상 절대 null 안됨 왜냐하면 등록하는 과정을 알기에 무조건 string 이 들어감
                            innerPostionPartNameSet.add(y.key!!) //서빙 담기
                            innerPostionPartNameSet.add(z.key!!) //오픈 담기
                        }
                    }
                    positionPartNameSet.add(innerPostionPartNameSet)
                    Log.d("jooan",positionPartNameSet.toString())
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })

        val dayItem : ArrayList<Button> = arrayListOf(mon,tue,wed,thr,fri,sat,sun)

        mon.setOnClickListener{
            for( btn in dayItem){
                if(btn==mon){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }


            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("월")
            fillPositionList("월")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }
        }

        tue.setOnClickListener{

            for( btn in dayItem){
                if(btn==tue){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("화")
            fillPositionList("화")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }

        }

        wed.setOnClickListener{
            for( btn in dayItem){
                if(btn==wed){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("수")
            fillPositionList("수")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }

        }
        thr.setOnClickListener{
            for( btn in dayItem){
                if(btn==thr){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("목")
            fillPositionList("목")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }

        }
        fri.setOnClickListener{
            for( btn in dayItem){
                if(btn==fri){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("금")
            fillPositionList("금")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }
        }
        sat.setOnClickListener{
            for( btn in dayItem){
                if(btn==sat){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("토")
            fillPositionList("토")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }
        }
        sun.setOnClickListener{
            for( btn in dayItem){
                if(btn==sun){
                    btn.setBackgroundColor(Color.rgb(250, 171, 26))
                }else{
                    btn.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            personLIst.clear()//시작하기전에 비워줘야 누적 안됨 액션에 따라 바뀌므로 액션 시작에 넣어줌

            fillPersonList("일")
            fillPositionList("일")

            position_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = PositionListAdapter(positionList)
            }
            worker_list.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)
                adapter = WorkerListAdapter(personLIst)
            }

        }
        //리스너 등록 끝
        //어뎁터에 넣어서 리사이클러 뷰 채워줘야 함

    }
    fun fillPersonList(day : String){
        val day = day
        for(k in positionPartNameSet){
            if(k[0].equals(day)){
                dirFire.child("WorkingPart").child(day).child(k[1]).child(k[2]).child("RequestList").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        for (j in p0.children){
                            val person = PositionApplyPerson(k[1],k[2],j.key!!) // 얘도 마찬가지 무조건 이름을 등록하게 되어있음 절대 null이 불가능 그러니 제발 믿어줘,,,
                            personLIst.add(person)
                        }
                    }
                    override fun onCancelled(p0: DatabaseError) {

                    }
                })
            }
        }
    }

    fun fillPositionList(day : String){
        val day = day
        positionList.clear()
        for(positions in positionPartNameSet){
            if(positions.get(0)==day){
                for(i in 0..(positions.size/3)-1){
                    val position: String = positions.get(3*i+1)
                    val part : String = positions.get(3*i+2)
                    val positionSet = PositionApplyPerson(positionName = position,partName = part)
                    positionList.add(positionSet)
                }
            }
        }
    }

}
