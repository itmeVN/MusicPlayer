package com.example.vietpc.musicplayer

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.vietpc.musicplayer.Adapter.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listSong : ArrayList<Song> = ArrayList()
    var adapter : SongAdapter? =null
    val PERMISSION_REQUEST_CODE = 12
    lateinit var db : SQLlite_song
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                   PERMISSION_REQUEST_CODE )
        }else{
            LoadData()
        }
    }
    fun LoadData(){
            db = SQLlite_song(this)
            //this.deleteDatabase("Mydatabase")
            var songCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null,null,null,null)
            while (songCursor !=null && songCursor.moveToNext()){
                var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                db.addSong(Song(songName,songDuration,2f,"",songPath))
                Log.d("Path", songPath)
            }

        listSong = db.getAllSongs() as ArrayList<Song>
        adapter = SongAdapter(listSong,applicationContext)
        var Layout = LinearLayoutManager(applicationContext)
        recycler_View.layoutManager = Layout
        recycler_View.adapter = adapter
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Cho phep truy cap",Toast.LENGTH_LONG).show()
                LoadData()
            }
        }
    }
}
