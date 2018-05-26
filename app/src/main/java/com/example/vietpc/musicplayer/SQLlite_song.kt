package com.example.vietpc.musicplayer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DataSetObservable
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLlite_song(context: Context) : SQLiteOpenHelper(context,"Mydatabase",null,1) {
    private final var table_name: String = "table_name"
    private final var song_name: String = "song_name"
    private final var song_time: String = "song_time"
    private final var song_rate: String = "song_rate"
    private final var song_cmt: String = "song_cmt"
    private final var song_path: String = "song_path"
    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = String.format("create table if not exists %s ( %s Text primary key, %s Text, %s Float , %s Text , %s Text)", table_name, song_name, song_time, song_rate, song_cmt,song_path)
        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists " + table_name)
        onCreate(db)
    }

    public fun addSong(song: Song) {
        var db: SQLiteDatabase = this.writableDatabase
        var values: ContentValues = ContentValues()
        values.put(song_name, song.sName)
        values.put(song_time, song.sTime)
        values.put(song_rate, song.sRate)
        values.put(song_cmt, song.sComment)
        values.put(song_path, song.sPath)
        db.insert(table_name, null, values)
        db.close()
    }

    public fun edit_song(song: Song) {
        var  db : SQLiteDatabase = this.writableDatabase
        var values : ContentValues = ContentValues()
        values.put(song_name, song.sName)
        values.put(song_time, song.sTime)
        values.put(song_rate, song.sRate)
        values.put(song_cmt, song.sComment)
        values.put(song_path, song.sPath)
        db.update(table_name,values,song_name + "=?", arrayOf(song.sName))
    }

    public fun getAllSongs(): List<Song> {
        var songlist: ArrayList<Song> = ArrayList()
        var sql = "select * from " + table_name + ";"
        var db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor = db.rawQuery(sql, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var song: Song = Song(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getString(3),
                        cursor.getString(4))
                songlist.add(song)
            }
        }
        return songlist
    }
}