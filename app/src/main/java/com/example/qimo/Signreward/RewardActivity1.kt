package com.example.qimo.Signreward

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.edu.sicnu.cardgame.Card
import com.example.qimo.*
import com.example.qimo.TABLE_NAME
import kotlinx.android.synthetic.main.activity_reward1.*

class RewardActivity1 : AppCompatActivity() {

    private val rewardlist = ArrayList<Reward>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward1)
        insertRewardlists()
        initRewardlists()
        val layoutManager = StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.VERTICAL)
        recyclerView_reward.layoutManager = layoutManager
        val adapter = RewardAdapter(rewardlist)
        recyclerView_reward.adapter = adapter


    }

   fun  initRewardlists() {
       val openSqLiteHelper = GameSQlite(this, 2)
       val db = openSqLiteHelper.writableDatabase
       val cursor = db.query(TABLE_NAME4, null, null, null, null, null, null)
       //,Rewarddate text,Reward integer
       if (cursor.moveToFirst()) {
           do {
               val rewardDate = cursor.getString(cursor.getColumnIndex("Rewarddate"))
               val reward = cursor.getString(cursor.getColumnIndex("Reward"))
               rewardlist.add(Reward(rewardDate, reward))
           } while (cursor.moveToNext())
       }
   }
    fun insertRewardlists() {
        val openSqLiteHelper = GameSQlite(this, 2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME4, null, null, null, null, null, null)
        //,Rewarddate text,Reward integer
        if (!cursor.moveToFirst()) {
            for (i in 1..31) {
                val contentValues = ContentValues().apply {
                    put("Rewarddate", "第${i}天")
                    put("Reward", 30 + i * 2)
                }
                db.insert(TABLE_NAME4, null, contentValues)
            }
            cursor.close()
        }
    }
}