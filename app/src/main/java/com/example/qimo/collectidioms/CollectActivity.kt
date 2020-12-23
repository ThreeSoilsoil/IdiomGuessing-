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

class CollectActivity : AppCompatActivity() {

    var bitmap =BitmapFactory.decodeResource(null,R.drawable.ic_launcher_background)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

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
            Log.d("cursor","${cursor}")
            val os = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG,100,os)
            val name = editText_Idiomname.text.toString()
            val explanation = editText_Idiomexplanation.text.toString()
            val contentValues = ContentValues().apply {
                put("name",name)
                put("Collection","☆")
                put("Explanation",explanation)
                put("image",os.toByteArray())
            }
            db.insert(TABLE_NAME3,null,contentValues)

            editText_Idiomexplanation.text.clear()
            editText_Idiomname.text.clear()
            Toast.makeText(this,"增加成功", Toast.LENGTH_SHORT).show()

        }
//        Button_delete.setOnClickListener {
//            val name = editText_Idiomname.text.toString()
//            db.delete(TABLE_NAME3,"name = ?", arrayOf(name))
//        }
//        Button_update.setOnClickListener {
//            val os = ByteArrayOutputStream()
//            bitmap?.compress(Bitmap.CompressFormat.PNG,100,os)
//            val name = editText_Idiomname.text.toString()
//            val explanation = editText_Idiomexplanation.text.toString()
//            val contentValues = ContentValues().apply {
//                put("name",name)
//                put("Explanation",explanation)
//                put("image",os.toByteArray())
//            }
//            db.update(TABLE_NAME3,contentValues,"name = ?", arrayOf(name))
//
//        }
//
//        Button_query.setOnClickListener {
//            val name = editText_Idiomname.text.toString()
//            val cursor = db.query(TABLE_NAME3,null,"name like '%$name%'", null,null,null,null)
//
//        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("1231","456456")
        when(requestCode){
            2 ->{
                Log.d("1232","456456")
                if(requestCode==2 && data !=null){
                    data.data?.let { uri ->
                        bitmap =getBitmapFromUri(uri)
                        imageView_detailct.setImageBitmap(bitmap)
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