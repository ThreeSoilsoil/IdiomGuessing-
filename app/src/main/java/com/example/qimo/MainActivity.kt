package com.example.qimo

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.edu.sicnu.cardgame.Card
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Playernames= arrayOf("张真人","孙大圣","猪八戒","空你急哇")
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
                    }
                    db.insert(TABLE_NAME2, null, contentValues)

            }
            val intent = Intent(this,GameActivity::class.java)
            startActivity(intent)
        }
    }

}
fun main(){
    val Playernames= arrayOf("张真人","孙大圣","猪八戒","空你急哇")
    val random = Random()
    val b=random.nextInt(Playernames.size-1)
    println(b)
    Playernames[b]
    var playername=Playernames[random.nextInt(Playernames.size)]
    println(playername)

}