package com.example.qimo

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cardgame.Card
import cn.edu.sicnu.cardgame.Card.Companion.idioms_count

import kotlinx.android.synthetic.main.fragment_s_qlite.*


class SQliteFragment : Fragment() {

    lateinit var adapter: MyRecyclerViewAdapter
    lateinit var db: SQLiteDatabase
    lateinit var cursor: Cursor
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSqLiteHelper = this.context?.let { GameSQlite(it,2 ) }
        if (openSqLiteHelper != null) {
            db = openSqLiteHelper.writableDatabase
        }

        //玩家表 gameId text,Honor text,percen text,Hcopper integer)
            val cursor2=db.query(TABLE_NAME2,null,null,null,null,null,null)
         if(cursor2.moveToFirst()){

            do {
                val playername=cursor2.getString(cursor2.getColumnIndex("gameId"))
                val honor=cursor2.getString(cursor2.getColumnIndex("Honor"))
                val percen=cursor2.getString(cursor2.getColumnIndex("percen"))
                val hcopper = cursor2.getString(cursor2.getColumnIndex("Hcopper"))
                textView_playerName.text="姓名："+playername
                textView_Honor.text="荣誉称号："+honor
                textView_Percen.text=percen
                textView_Hcopper.text=hcopper
            }while(cursor2.moveToNext())
        }
        cursor2.close()

            val cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
            adapter = MyRecyclerViewAdapter(cursor)
            recyclerView.adapter = adapter

            val layoutManager = LinearLayoutManager(this.context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = layoutManager
        //初始化关卡数据
        val cursor1 = db.query(TABLE_NAME,null,null,null,null,null,null)
        if(cursor1.moveToFirst()){
        }else{
            for(i in 1..idioms_count) {
                val contentValues = ContentValues().apply {
                    put("checkpoint","第${i}关")
                    put("score","☆☆☆")
                    put("ispass","未通过")
                    put("time","无")
                    put("Getcopper",50)
                    put("lockgrade",0)
                }
                db.insert(TABLE_NAME, null, contentValues)
            }
        }

            button_queryAll.setOnClickListener {
                reloadAllData()
            }

            button_queryNopass.setOnClickListener {
                val ispass="未通过"
               // val cursor = db.query(TABLE_NAME,null,"ispass = ?", arrayOf("$ispass"),null,null,null)
                val cursor = db.query(TABLE_NAME,null,"ispass like '%$ispass%'", null,null,null,null)
                adapter.swapCursor(cursor)
            }

            button_queryPass.setOnClickListener {
                val ispass="已过关"
                //val cursor = db.query(TABLE_NAME,null,"ispass = ?",  arrayOf("$ispass"),null,null,null)
                val cursor = db.query(TABLE_NAME,null,"ispass like '%$ispass%'", null,null,null,null)

                adapter.swapCursor(cursor)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_s_qlite, container, false)
    }
    private fun reloadAllData() {
        cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        adapter.swapCursor(cursor)
    }

}



class MyRecyclerViewAdapter(var cursor: Cursor): RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    fun swapCursor(newCursor: Cursor) {
        if (cursor == newCursor) return
        cursor.close()
        cursor = newCursor
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_Name: TextView
        val textView_Age: TextView
        val textView_Time:TextView

        init {
            textView_Name = view.findViewById(R.id.textView_checkpoint)
            textView_Age = view.findViewById(R.id.textView_score)
            textView_Time=view.findViewById(R.id.textView_time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sqlite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.textView_Name.text = cursor.getString(cursor.getColumnIndex("checkpoint"))
        holder.textView_Age.text = cursor.getString(cursor.getColumnIndex("score"))
        holder.textView_Time.text = cursor.getString(cursor.getColumnIndex("time"))

    }

    override fun getItemCount(): Int {
        return cursor.count
    }
}