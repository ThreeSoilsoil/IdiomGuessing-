package com.example.qimo.collectidioms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qimo.*
import com.example.qimo.checkpoint.CheckPoints
import com.example.qimo.checkpoint.ChecpointAdapter
import kotlinx.android.synthetic.main.activity_list_ct.*

class ListCtActivity : AppCompatActivity() {
    val checkpointss=ArrayList<CheckPoints>()
    lateinit var adapter: ChecpointAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ct)
            init()
            adapter = ChecpointAdapter(this,R.layout.checkpoint_item,
                checkpointss
            )
        listView_collect.adapter=adapter
        listView_collect.setOnItemClickListener { adapterView, view, position, l ->
                val intent = Intent(this,DetailsCtActivity::class.java)
                intent.putExtra("idiomName",checkpointss[position].guanka)
                startActivity(intent)
            }
    }

    override fun onRestart() {
        super.onRestart()
        checkpointss.clear()
        init()
        //初始化关卡数据
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        adapter = ChecpointAdapter(this,R.layout.checkpoint_item,
            checkpointss
        )
        listView_collect.adapter=adapter
        listView_collect.setOnItemClickListener { adapterView, view, position, l ->
            val intent = Intent(this,DetailsCtActivity::class.java)
            intent.putExtra("idiomName",checkpointss[position].guanka)
            startActivity(intent)
        }
    }
    fun init(){
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME3,null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val checkpoint=cursor.getString(cursor.getColumnIndex("name"))
                val score=cursor.getString(cursor.getColumnIndex("Collection"))
                checkpointss.add(CheckPoints(checkpoint,score))
            }while(cursor.moveToNext())
        }
        cursor.close()
    }
}