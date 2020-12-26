package com.example.qimo.Signreward

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.qimo.*
import java.text.SimpleDateFormat
import java.util.*

class RewardAdapter (val rewardList: List<Reward>):
    RecyclerView.Adapter<RewardAdapter.ViewHolder>(){
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val rewardDate:TextView = view.findViewById(R.id.TextView_rewardDate)
        val reward:Button = view.findViewById(R.id.Button_reward)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reward_item,parent,false)
        val viewHolder = ViewHolder(view)
        viewHolder.reward.setOnClickListener {
            val position =viewHolder.adapterPosition
            Log.d("adapterposition","${position}")
            val openSqLiteHelper = GameSQlite(parent.context, 2)
            val db = openSqLiteHelper.writableDatabase

            val date = Date()  //获取当前天数
            val dateFormat= SimpleDateFormat("第dd天")
            val time=dateFormat.format(date)
            var rewardDate=""  //与当前天数比较
            var issign=""  //判断是否已经签到和得到需要加的铜钱数量


            val cursor = db.query(TABLE_NAME4, null, "Rewarddate like '%第${position+1}天%'", null, null, null, null)
            //,Rewarddate text,Reward integer
            if (cursor.moveToFirst()) {
                do {
                     rewardDate = cursor.getString(cursor.getColumnIndex("Rewarddate"))
                    issign = cursor.getString(cursor.getColumnIndex("Reward"))
                } while (cursor.moveToNext())
            }
            Log.d("adapterposition","${rewardDate}")
            if(rewardDate==time && issign!="√"){
                viewHolder.reward.text="√"
                Toast.makeText(parent.context,"签到成功，奖励已发放！",Toast.LENGTH_SHORT).show()

                val value = ContentValues().apply {
                    put("Hcopper",getHcopper(db)+issign.toInt())
                }
                Log.d("adapterposition","${getHcopper(db)+issign.toInt()}")
                db.update(TABLE_NAME2,value,null,null)

                val contentValues = ContentValues().apply {
                    put("Reward", "√")
                }
                db.update(TABLE_NAME4,contentValues,"Rewarddate = ?", arrayOf("第${position+1}天"))
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RewardAdapter.ViewHolder, position: Int) {
        val rewards = rewardList[position]
        holder.rewardDate.text=rewards.rewardDate
        holder.reward.text=rewards.reward
    }

    override fun getItemCount(): Int {
      return rewardList.size
    }
    fun getHcopper(db:SQLiteDatabase):Int{
        var hcopper=0
        //val openSqLiteHelper = GameSQlite(context = parent.context,2)
        //val db = openSqLiteHelper.writableDatabase
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