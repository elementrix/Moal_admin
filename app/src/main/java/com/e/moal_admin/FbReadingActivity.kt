package com.e.moal_admin

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_seewhatyougot.*

import android.util.Log


class FbReadingActivity :AppCompatActivity(){

    var rootRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val dirFire: DatabaseReference = rootRef.child("노랑통닭 개화산점")
    val list :ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_seewhatyougot)

        dirFire.child("StoreInfo").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val info: StoreInfo? = p0.getValue(StoreInfo::class.java)
                if (info != null) {
                    read_from_db.setText("Storinfo: name: "+info.name+"\nAddress: "+info.Address)
                    Log.d("Jooan","Storinfo: name: "+info.name+" Address: "+info.Address )
                }else{
                    Log.d("Jooan","Storinfo fail")
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
//        val reqMap = mutableMapOf<String,String>()
//        reqMap.clear()
//        dirFire.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot) {
//                for(h in p0.children){
//                    val req = ""+h.value
//                    reqMap[h.key!!]=req
//                    Log.d("Jooan","value = 진짜값: $req")
//                }
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//        })
//        Log.d("Jooan","i'm: "+reqMap)
    }
}