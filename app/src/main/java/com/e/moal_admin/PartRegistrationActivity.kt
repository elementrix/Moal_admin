package com.e.moal_admin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_parttime.*

class PartRegistrationActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parttime)

        window.statusBarColor = Color.parseColor("#344955")

        val intent = Intent(this, MainActivity::class.java)

        btn_complete.setOnClickListener{
            startActivity(intent)
            toast("파트타임 등록을 완료했습니다.")
        }

    }
    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}