package com.example.qimo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "mydb.db"
const val TABLE_NAME = "Checkpoint"
const val TABLE_NAME2 = "Player"
const val TABLE_NAME3 = "Collect"
val a:Boolean=false
class GameSQlite(context:Context,version:Int): SQLiteOpenHelper(context, DB_NAME,null,version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_NAME(_id integer primary key autoincrement, checkpoint text,score text,ispass text,time text,Getcopper integer,lockgrade integer)")
        db?.execSQL("create table $TABLE_NAME2(_id integer primary key autoincrement, gameId text,Honor text,percen text,Hcopper integer,Reward text)")
        db?.execSQL("create table $TABLE_NAME3(_id integer primary key autoincrement, name text,Explanation text,image BLOB,Collection text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //TODO("Not yet implemented")
        db?.execSQL("drop table if exists $TABLE_NAME")
        db?.execSQL("drop table if exists $TABLE_NAME2")
        db?.execSQL("drop table if exists $TABLE_NAME3")
        onCreate(db)
    }

}