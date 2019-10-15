package com.e.moal_admin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.app_bar_calendar.*

class CalendarActivity :AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var registrationFragment: RegistrationFragment
    lateinit var notificationFragment: NotificationFragment
    lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        window.statusBarColor = Color.parseColor("#344955")

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setTitle("") //텍스트뷰로 센터맞추고 하기가 더 쉬워서 사용 안할거임

        val drawerToggle = object : androidx.appcompat.app.ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            (R.string.open), (R.string.close)
        ) {

        }

        drawerToggle.isDrawerIndicatorEnabled = true //뭔뜻이니
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //now implement navigation item selected listener
        //The default fragment is homeFragment
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.Frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        // so this is our fragment code
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        //now create related fragment

        val title:TextView = fragment_title
        val intentMainActivity =  Intent(this, MainActivity::class.java)
        //로그아웃에 필요함

        when (menuItem.itemId) {
            R.id.home -> {
                title.setText("Home")
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.Frame_layout, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            // so this is our fragment code
            R.id.registration -> {
                title.setText("Registration")
                registrationFragment = RegistrationFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.Frame_layout, registrationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.notification -> {
                title.setText("Notification")
                notificationFragment = NotificationFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.Frame_layout, notificationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            /*
            R.id.setting -> {
                settingFragment = SettingFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.Frame_layout, settingFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            */
            R.id.logout -> {
                toast("로그아웃 되었습니다")
                startActivity(intentMainActivity)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}