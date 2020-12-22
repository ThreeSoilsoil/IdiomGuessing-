package com.example.qimo


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.qimo.settingfragment.SettingFragment
//import com.example.fragmentdemo.gamefragment.GameFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class GameActivity : AppCompatActivity() {
    val fragment1 = CheckpointFragment()
    val fragment2 = SQliteFragment()
    val fragment3 = SettingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,fragment1)
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNV)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.game    ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment1)
                        .commit()
                R.id.record ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment2)
                        .commit()
                R.id.setting ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment3)
                        .commit()
            }
            true
        }

    //    val intentFilter = IntentFilter(CHAT_INTENT)
    //    val receiver = MyReceiver()
     //   registerReceiver(receiver,intentFilter)

    }

    fun printFragments() {
        supportFragmentManager.fragments.forEach {
            Log.d("Fragment","id: ${it}")
        }

    }

}

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("BroadcastReceiver","onReceive")
    }

}