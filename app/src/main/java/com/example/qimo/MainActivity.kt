package com.example.qimo

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import cn.edu.sicnu.cardgame.Card
import com.example.qimo.Signreward.RewardActivity
import com.example.qimo.collectidioms.CollectActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.text.DateFormat as DateFormat1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val Playernames= arrayOf("张真人","孙大圣","猪八戒","空你急哇") //初始化用户名
        val random = Random()
        var playername=Playernames[random.nextInt(Playernames.size)]

        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME2,null,null,null,null,null,null)

        button_startGame.setOnClickListener {
           // gameId text,Honor text,percen text,Hcopper integer)
            if(!cursor.moveToFirst()){
                Log.d("wanjianba","156456464")
                    val contentValues = ContentValues().apply {
                        put("gameId",playername)
                        put("Honor","无")
                        put("percen","0/0")
                        put("Hcopper",0)
                        put("Reward","日")
                    }
                    db.insert(TABLE_NAME2, null, contentValues)

            }
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signreward -> {
                val intent=Intent(this,RewardActivity::class.java)
                startActivity(intent)
            }
            R.id.collect -> {
                val intent=Intent(this, CollectActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
fun main(){
    val date =Date()
    val time =date.toLocaleString()
//    Log.d("time ","${time}")
    val dateFormat=SimpleDateFormat("dd日")
    val time2=dateFormat.format(date)
  //  Log.d("time2","${time2}")
    println("time "+time)
    println("time2 "+time2)
}