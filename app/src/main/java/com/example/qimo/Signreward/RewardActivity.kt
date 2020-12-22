package com.example.qimo.Signreward

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.edu.sicnu.cardgame.Card
import com.example.qimo.GameSQlite
import com.example.qimo.R
import com.example.qimo.TABLE_NAME2
import kotlinx.android.synthetic.main.activity_reward.*
import java.text.SimpleDateFormat
import java.util.*

class RewardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        //获取当前日期（天数）
        val date = Date()
        val dateFormat= SimpleDateFormat("yyyy年-mm月dd日")
        val now_time=dateFormat.format(date)
        //Log.d("time2","${time2}")

        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase

        var time=" "
        val cursor = db.query(TABLE_NAME2,null,null, null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                time=cursor.getString(cursor.getColumnIndex("Reward"))
            }while(cursor.moveToNext())
        }
        cursor.close()

        //作比较
        if(now_time==time){
            button_reward.text="已签到"
            textView_reward.text="恭喜你完成了今天的签到任务"
            button_reward.isEnabled=false
        }else{
            val hcopper= getHcopper()
            button_reward.isEnabled=true
            textView_reward.text="+50"
            button_reward.text="签到"
            val contentValues = ContentValues().apply {
                put("Hcopper",hcopper+50)
                put("Reward",now_time)
            }
            db.update(TABLE_NAME2,contentValues,null, null)
            button_reward.text="已签到"
            textView_reward.text="恭喜你完成了今天的签到任务"
            button_reward.isEnabled=false
        }
    }

    fun getHcopper():Int{
        var hcopper=0
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME2,null,null, null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                hcopper=cursor.getInt(cursor.getColumnIndex("Hcopper"))
            }while(cursor.moveToNext())
        }
        cursor.close()
        return hcopper
    }
}