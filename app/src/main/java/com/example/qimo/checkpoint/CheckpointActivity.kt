package com.example.qimo.checkpoint


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.qimo.*
import com.example.qimo.gamefragment.Game1Activity


import kotlinx.android.synthetic.main.activity_checkpoint.*


class CheckpointActivity : AppCompatActivity() {

    val checkpointss=ArrayList<CheckPoints>()
    lateinit var adapter: ChecpointAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkpoint)

        runTimer() //记录时间
        initCheckpointss()
        //初始化关卡数据
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
         adapter = ChecpointAdapter(this, R.layout.checkpoint_item,
            checkpointss
        )
        listView.adapter=adapter
        listView.setOnItemClickListener { adapterView, view, i, l ->
            // adapter.notifyDataSetChanged()
            running =false     //解决时间问题，直接通过滑动屏幕返回
            second =0

            val imageindex = i
            val guanka="第${imageindex}关"
            if(imageindex>0){
                val cursor = db.query(TABLE_NAME,null,"checkpoint like '%$guanka%'", null,null,null,null)
                if(cursor.moveToFirst()){
                    val ispass=cursor.getString(cursor.getColumnIndex("ispass"))
                    if(ispass=="未通过"){
                        Toast.makeText(this,"请通过前面的关卡！！！", Toast.LENGTH_SHORT)
                            .show()
                    } else{
                        val intent = Intent(this, Game1Activity::class.java)
                        intent.putExtra("imageindex",imageindex)
                        startActivity(intent)
                    }
                }

            }else{
                val intent = Intent(this, Game1Activity::class.java)
                intent.putExtra("imageindex",imageindex)
                startActivity(intent)
            }

        }
    }
    override fun onRestart() {
        super.onRestart()
        checkpointss.clear()
        initCheckpointss()
        //初始化关卡数据
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        adapter = ChecpointAdapter(this, R.layout.checkpoint_item,
            checkpointss
        )
        listView.adapter=adapter
        listView.setOnItemClickListener { adapterView, view, i, l ->

            running =false     //解决时间问题，直接通过滑动屏幕返回
            second =0
            val imageindex = i
            val guanka="第${imageindex}关"
            if(imageindex>0){
                val cursor = db.query(TABLE_NAME,null,"checkpoint like '%$guanka%'", null,null,null,null)
                if(cursor.moveToFirst()){
                    val ispass=cursor.getString(cursor.getColumnIndex("ispass"))
                    if(ispass=="未通过"){
                        Toast.makeText(this,"请通过前面的关卡！！！", Toast.LENGTH_SHORT)
                            .show()
                    } else{
                        val intent = Intent(this, Game1Activity::class.java)
                        intent.putExtra("imageindex",imageindex)
                        startActivity(intent)
                    }
                }

            }else{
                val intent = Intent(this, Game1Activity::class.java)
                intent.putExtra("imageindex",imageindex)
                startActivity(intent)
            }

        }
    }

    fun initCheckpointss(){
    val openSqLiteHelper = GameSQlite(this,2)
    val db = openSqLiteHelper.writableDatabase
    val cursor1 = db.query(TABLE_NAME,null,null,null,null,null,null)
    if(cursor1.moveToFirst()){
        do {
            val checkpoint=cursor1.getString(cursor1.getColumnIndex("checkpoint"))
            val score=cursor1.getString(cursor1.getColumnIndex("score"))
            checkpointss.add(CheckPoints(checkpoint,score))
        }while(cursor1.moveToNext())
    }
    cursor1.close()

//    listView.adapter=adapter
}


    companion object{
        var second = 0
        var running = false
        var time=""
        fun runTimer(){
            val handler = Handler()
            val runnable = object : Runnable{
                override fun run() {
                    val hours = second / 3600
                    val minutes = (second % 3600) / 60
                    val secs = second % 60
                    time = String.format("%02d:%02d:%02d", hours, minutes, secs)
                    if (running) {
                        second++
                    }
                    handler.postDelayed(this, 2000)
                }
            }
            handler.post(runnable)
        }

    }

}