package com.example.alex.wheaterapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBase(context: Context) : SQLiteOpenHelper(context, Utils.DATA_BASE_NAME, null, Utils.DATA_BASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                CELCIUS + " TEXT," + FARENHEIT + " TEXT," +
                DATE + " TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addRecord(record: Record){
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CELCIUS, record.celcius)
        values.put(FARENHEIT, record.farenheit)
        values.put(DATE, record.date)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
    }

    fun getAllUsers(): String {
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val date = cursor.getString(cursor.getColumnIndex(DATE))
                    val celcius = cursor.getString(cursor.getColumnIndex(CELCIUS))
                    val farenheit = cursor.getString(cursor.getColumnIndex(FARENHEIT))

                    allUser = "$allUser\n$date $celcius $farenheit"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }

    companion object {
        
        private val TABLE_NAME = "Record"
        private val ID = "Id"
        private val CELCIUS = "Celcuis"
        private val FARENHEIT = "Farenheit"
        private val DATE = "Date"
    }

}