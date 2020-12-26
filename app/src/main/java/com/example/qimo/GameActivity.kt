package com.example.qimo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.qimo.checkpoint.CheckpointFragment
import com.example.qimo.collectidioms.CollectActivity
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

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.collectmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.collect -> {
                val intent=Intent(this, CollectActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}

//class MyReceiver: BroadcastReceiver() {
//    override fun onReceive(p0: Context?, p1: Intent?) {
//        Log.d("BroadcastReceiver","onReceive")
//    }
//
//}