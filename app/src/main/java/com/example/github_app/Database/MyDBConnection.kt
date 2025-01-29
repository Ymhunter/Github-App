package com.example.github_app.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBConnection(var context: Context, var dbName: String, var dbversion: Int): SQLiteOpenHelper(context, dbName, null, dbversion)
{
    companion object{
        val tableName: String="UserDBInfo"
        val col1:String="uid"
        val col2:String="Username"
        val col3: String="Date"
    }
    val query: String="create table "+ tableName+" ("+col1+" integer primary key autoincrement, "+col2+" text, "+col3+" text)"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(query)
    }
    override fun onUpgrade (db: SQLiteDatabase?, p1: Int, p2: Int) {
    db?.execSQL("drop table if exits "+ tableName)
    db?.execSQL(query)
}
    fun insert (userName: String, date: String): Long
    {
        val db:SQLiteDatabase=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(col2, userName)
        contentValues.put(col3, date)
        val result: Long= db.insert(tableName,null, contentValues)
        return result
    }
}