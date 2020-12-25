package com.example.qimo.collectidioms

import android.content.ContentValues
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.qimo.GameSQlite
import com.example.qimo.R
import com.example.qimo.TABLE_NAME3
import kotlinx.android.synthetic.main.activity_details_ct.*
import java.io.ByteArrayInputStream

class DetailsCtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_ct)
        var name = intent.getStringExtra("idiomName")

        val openSqLiteHelper = GameSQlite(this,2)
        val db = openSqLiteHelper.writableDatabase

        val cursor = db.query(TABLE_NAME3,null,"name like '%$name%'", null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                TextView_Idiomname.text=cursor.getString(cursor.getColumnIndex("name"))
                TextView_explanation.text=cursor.getString(cursor.getColumnIndex("Explanation"))
                Button_collection.text=cursor.getString(cursor.getColumnIndex("Collection"))

                imageView_detailct2.setImageDrawable(Drawable.createFromStream(ByteArrayInputStream( cursor.getBlob(cursor.getColumnIndex("image"))),"img"))
                TextView_Date.text=cursor.getString(cursor.getColumnIndex("Date"))
            }while(cursor.moveToNext())
        }
        cursor.close()
        Button_delete.setOnClickListener {
            db.delete(TABLE_NAME3,"name = ?", arrayOf(name))
            finish()
        }
        Button_collection.setOnClickListener {
            if( Button_collection.text.toString()=="☆"){
                Button_collection.text="★"
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