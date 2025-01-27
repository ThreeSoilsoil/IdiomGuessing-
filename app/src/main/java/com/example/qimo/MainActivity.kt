package com.example.qimo

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.qimo.Signreward.RewardActivity1
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

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

            val mediaPlayer = MediaPlayer.create(this, R.raw.kaishiyouxi)//msg是滴的MP3文件，很小
            mediaPlayer.start()


           // gameId text,Honor text,percen text,Hcopper integer)
            if(!cursor.moveToFirst()){
                Log.d("wanjianba","156456464")
                    val contentValues = ContentValues().apply {
                        put("gameId",playername)
                        put("Honor","无")
                        put("percen","0/0")
                        put("Hcopper",0)
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
                val intent=Intent(this, RewardActivity1::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
fun main(){
    val date =Date()
    val time =date.toLocaleString()
    var time3 =""
    for(i in 0..10){
        time3=time3+time[i]
    }
//    Log.d("time ","${time}")
    val dateFormat=SimpleDateFormat("第dd天")
    val time2=dateFormat.format(date)
  //  Log.d("time2","${time2}")
    println("time "+time)
    println("time2 "+time2)
    println("time3 "+time3)
}