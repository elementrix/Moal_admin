package com.e.moal_admin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_main.*
import java.nio.channels.CancelledKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

//import com.rey.material.app.Dialog
//import com.rey.material.app.DialogFragment


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().reference
    var number =1
    var rootRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val dirFire: DatabaseReference = rootRef.child("노랑통닭 개화산점")

    //request 값으로 받아올 변수를 설정(바꿀 필요없으므로 val)
    private val PERMISSIONS_REQUEST_CODE = 100

    //필요한 리퀘스트를 담아놓은 어레이(바꿀 필요없으므로 val)
    private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.INTERNET,android.Manifest.permission.ACCESS_FINE_LOCATION)

    @SuppressLint("PackageManagerGetSignatures")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val mDialog = Dialog(this,3)
//
//        mDialog.applyStyle(3)
//            .title("Example")
//            .positiveAction("OK")
//            .negativeAction("CANCEL")
//            .cancelable(true)
//            .show()
//
//        val builder: Builder
//        newInstance(builder)

        val intentStoreRegistrationActivity = Intent(this, StoreRegistrationActivity::class.java)
        val intentCalendarActivity = Intent(this, CalendarActivity::class.java)
        // 이동할 화면을 지정
        // intent는 이동할 화면이므로 바뀌지 않음 따라서 val

        window.statusBarColor = Color.parseColor("#344955")

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            //OS가 마쉬멜로 이상일 경우 권한체크
            val permissionCheckInternet
                    = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
            val permissionCheckAccessFineLocation
                    = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)

            //만약 두개다 권한이 있을경우
            if (permissionCheckInternet == PackageManager.PERMISSION_GRANTED
                && permissionCheckAccessFineLocation == PackageManager.PERMISSION_GRANTED){

                Log.d("Debug","권한 이미있음")
            }else{ //권한이 없으면
                Log.d("Debug","권한 없음")

                ActivityCompat.requestPermissions(this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE)
            }
        }else{
            //OS가 마쉬멜로 이전일 경우 권한체크가 필요없으므로
            Log.d("Debug","마쉬멜로 버전 이하인 관계로 권한이 이미 있음")
        }


        btn_complete.setOnClickListener{
            writeNewUser(userId = "userNo - "+ number ,name=id.text.toString(), email = password.text.toString())
            number++
            startActivity(intentCalendarActivity)
        }

        register.setOnClickListener {
            startActivity(intentStoreRegistrationActivity)
        }

        dirFire.child("StoreInfo").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val info: StoreInfo? = p0.getValue(StoreInfo::class.java)
                if (info != null) {
                    Log.d("Jooan","Storinfo: name: "+info.name+" Address: "+info.Address )
                }else{
                    Log.d("Jooan","Storinfo fail")
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }

    private fun writeNewUser (userId: String, name: String, email: String){
        val user = User(name,email)
        database.child("users").child(userId).setValue(user)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // grantResults[0] 거부 -> -1
        // grantResults[0] 허용 -> 0 (PackageManager.PERMISSION_GRANTED)

        Log.d("Debug", "requestCode : $requestCode, grantResults size : ${grantResults.size}")

        if(requestCode == PERMISSIONS_REQUEST_CODE){
            //권한이

            var check_result = true

            for(result in grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false
                    break
                }
            }

            if(check_result){
                Log.d("Debug","권한 승인")
            }else{
                Log.d("Debug","권한 거부")
            }
        }
    }

//    interface Builder {
//        fun build(context: Context): com.rey.material.app.Dialog
//        fun onPositiveActionClicked(fragment: DialogFragment)
//        fun onNegativeActionClicked(fragment: DialogFragment)
//        fun onNeutralActionClicked(fragment: DialogFragment)
//    }
//
//    fun newInstance(builder: Builder) {}
}
