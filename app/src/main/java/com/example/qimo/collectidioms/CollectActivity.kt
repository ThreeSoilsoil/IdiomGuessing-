package com.example.qimo.collectidioms

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.qimo.GameSQlite
import com.example.qimo.R
import com.example.qimo.TABLE_NAME3
import kotlinx.android.synthetic.main.activity_collect.*
import java.io.ByteArrayOutputStream
import java.util.*

class CollectActivity : AppCompatActivity() {

    var bitmap =BitmapFactory.decodeResource(null,R.drawable.add)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        Log.d("bitmap","${bitmap}")
        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        val cursor = db.query(TABLE_NAME3,null,null,null,null,null,null)
        Button_choose.setOnClickListener {
            val intent =Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,2)
        }
        Button_add.setOnClickListener {
            //imageView3.resources
            val name = editText_Idiomname.text.isEmpty()
            if(editText_Idiomname.text.isEmpty()){
                Toast.makeText(this,"成语名字不能为空！！！", Toast.LENGTH_SHORT).show()
            }else{

                val date = Date()
                val tim =date.toLocaleString()
                var time =""
                for(i in 0..10){
                    time+=tim[i]
                }
                val os = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG,100,os)
                val explanation = editText_Idiomexplanation.text.toString()
                val contentValues = ContentValues().apply {
                    put("name",editText_Idiomname.text.toString())
                    put("Collection","☆")
                    put("Explanation",explanation)
                    put("image",os.toByteArray())
                    put("Date",time)
                }
                db.insert(TABLE_NAME3,null,contentValues)
                editText_Idiomexplanation.text.clear()
                editText_Idiomname.text.clear()
                imageView_detailct2.setImageResource(R.drawable.add)
                Toast.makeText(this,"增加成功${editText_Idiomname.text}", Toast.LENGTH_SHORT).show()
            }
            Button_caccel.setOnClickListener {
                imageView_detailct2.setImageResource(R.drawable.add)
                editText_Idiomname.text.clear()
                editText_Idiomexplanation.text.clear()
            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            2 ->{
                if(requestCode==2 && data !=null){
                    data.data?.let { uri ->
                        bitmap =getBitmapFromUri(uri)
                        Log.d("bitmap2","${bitmap}")
                        imageView_detailct2.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    private fun getBitmapFromUri(uri: Uri)=contentResolver.openFileDescriptor(uri,"r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.collectmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Listcollect -> {
                val intent=Intent(this, ListCtActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}