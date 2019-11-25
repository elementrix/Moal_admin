package com.e.moal_admin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_registration.*

class UserRegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        window.statusBarColor = Color.parseColor("#344955")

        registerbutton.setOnClickListener {
            val emailIs = email_register.text.toString()
            val passwordIs = password_register.text.toString()
            var storename = ""
            var storeaddress = ""
            if (intent.hasExtra("storenameIs")){ storename= intent.getStringExtra("storenameIs") }
            if (intent.hasExtra("storeaddressIs")){ storeaddress= intent.getStringExtra("storeaddressIs")
            }


            //email은 이메일 형식으로, password는 길이 6 이상으로. 아니면 failure됨.
            if (emailIs.isEmpty() || passwordIs.isEmpty()){
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailIs, passwordIs)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    else{
                        val uid = FirebaseAuth.getInstance().uid ?:""
                        val ref = FirebaseDatabase.getInstance().reference.child("users").child("/admins/$uid")
                        val profileUpdate = UserProfileChangeRequest.Builder()
                            .setDisplayName(storename)
                            .build()
                        val userupdate = FirebaseAuth.getInstance().currentUser
                        val user = UserRegister(uid, storename, storeaddress)

                        ref.setValue(user)
                        userupdate?.updateProfile(profileUpdate)

                        finish()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "외않돼지?.", Toast.LENGTH_SHORT).show()

                }

        }

    }

    class UserRegister(val uid : String, val name : String, val storeaddress : String)
}