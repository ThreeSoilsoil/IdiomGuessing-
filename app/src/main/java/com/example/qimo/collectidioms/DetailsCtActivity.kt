package com.example.qimo.collectidioms

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.qimo.GameSQlite
import com.example.qimo.R
import com.example.qimo.TABLE_NAME3
import kotlinx.android.synthetic.main.activity_details_ct.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DetailsCtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_ct)
        var name = intent.getStringExtra("idiomName")

        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase
        Log.d("4564654","${name}")
        val cursor = db.query(TABLE_NAME3,null,"name like '%$name%'", null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                TextView_Idiomname.text=cursor.getString(cursor.getColumnIndex("name"))
                TextView_explanation.text=cursor.getString(cursor.getColumnIndex("Explanation"))
                Button_collection.text=cursor.getString(cursor.getColumnIndex("Collection"))
                imageView_detailct.setImageDrawable(Drawable.createFromStream(ByteArrayInputStream( cursor.getBlob(cursor.getColumnIndex("image"))),"img"))
            }while(cursor.moveToNext())
        }
        cursor.close()
        Button_collection.setOnClickListener {
            Log.d("4564654","${Button_collection.text.toString()}")
            if( Button_collection.text.toString()=="☆"){
                Button_collection.text="★"
                Log.d("4564654","${Button_collection.text.toString()}")
                val contentValues = ContentValues().apply {
                    put("Collection","${Button_collection.text}")
                }
                db.update(TABLE_NAME3,contentValues,"name = ?", arrayOf(name))
            }else if (Button_collection.text.toString()=="★"){
                Button_collection.text="☆"
                val contentValues = ContentValues().apply {
                    put("Collection","${Button_collection.text}")

                }
                db.update(TABLE_NAME3,contentValues,"name = ?", arrayOf(name))
            }

        }
    }
}