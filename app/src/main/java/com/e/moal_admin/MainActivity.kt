package com.e.moal_admin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().reference
    var number =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, StoreRegistrationActivity::class.java)
        // 이동할 화면을 지정
        // intent는 이동할 화면이므로 바뀌지 않음 따라서 val

        window.statusBarColor = Color.parseColor("#344955")


        btn_complete.setOnClickListener{
            writeNewUser(userId = "userNo - "+ number ,name=id.text.toString(), email = password.text.toString())
            number++
        }

        register.setOnClickListener {
            startActivity(intent)
        }

    }

    private fun writeNewUser (userId: String, name: String, email: String){
        val user = User(name,email)
        database.child("users").child(userId).setValue(user)
    }
}
