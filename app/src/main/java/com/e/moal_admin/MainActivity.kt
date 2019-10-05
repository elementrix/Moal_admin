package com.e.moal_admin

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

        btn_complete.setOnClickListener{
            writeNewUser(userId = "userNo - "+ number ,name=id.text.toString(), email = password.text.toString())
            number++
        }



    }

    private fun writeNewUser (userId: String, name: String, email: String){
        val user = User(name,email)
        database.child("users").child(userId).setValue(user)
    }
}
