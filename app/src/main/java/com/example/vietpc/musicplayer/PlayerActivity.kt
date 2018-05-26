package com.example.vietpc.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.preference.Preference
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_player.*
import java.io.*
import java.nio.BufferUnderflowException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.lang.System.`in`
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class PlayerActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, RatingBar.OnRatingBarChangeListener {
    lateinit var db: SQLlite_song
    var pos: Int = 0
    var listSong: ArrayList<Song> = ArrayList()
    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        db = SQLlite_song(this)
        pos = receive_Song()
        KhoiTao(pos)


        ratingBar.setOnRatingBarChangeListener(this)
        // play button event
        button_Play.setOnClickListener {
            if (mediaPlayer!!.isPlaying()) {
                mediaPlayer!!.pause()
                button_Play.setBackgroundResource(R.drawable.play)
            } else {
                mediaPlayer!!.start()
                //updateTime(pos)
                button_Play.setBackgroundResource(R.drawable.pause)
            }
            update()
        }
        // next button event
        button_Next.setOnClickListener {
            pos++
            if (pos > listSong.size - 1) {
                pos = 0;
            }
            if (mediaPlayer!!.isPlaying()) {
                mediaPlayer!!.stop()
            }
            KhoiTao(pos)
            mediaPlayer!!.start()
            button_Play.setBackgroundResource(R.drawable.pause)
        }
        // prev button evevt
        button_Prev.setOnClickListener {
            pos--
            if (pos < 0)
                pos = listSong.size - 1;
            if (mediaPlayer!!.isPlaying)
                mediaPlayer!!.stop()
            KhoiTao(pos)
            mediaPlayer!!.start()
            button_Play.setBackgroundResource(R.drawable.pause)
        }
        button_comment.setOnClickListener {
            var intent: Intent = Intent(this, comment_act::class.java)
            var bundle: Bundle = Bundle()
            bundle.putString("comment", listSong.get(pos).sComment)
            intent.putExtras(bundle)
            this.startActivityForResult(intent, 100)
        }

        // seekbar event
        seekBar.setOnSeekBarChangeListener(this)


    }

    public fun KhoiTao(vt: Int) {
        try {
            var giay: Long = listSong.get(vt).sTime.toLong()
            seekBar.max = giay.toInt()
            var time = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(giay),
                    TimeUnit.MILLISECONDS.toSeconds(giay) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(giay)))
            textView_TenBH.setText(listSong.get(vt).sName)
            textView_TG.setText(time)
            ratingBar.rating = listSong.get(vt).sRate!!
            mediaPlayer = MediaPlayer()
            var fd: FileDescriptor? = null
            var audioPath: String =  listSong.get(vt).sPath
//            // them
//            var file : File = File.createTempFile("nhac",".ogg",getCacheDir())
//            file.deleteOnExit()
//            var  fos :FileOutputStream = FileOutputStream(file)
//            fos.close()
//
//            //
            var fis: FileInputStream = FileInputStream(audioPath)
            fd = fis.fd
            mediaPlayer!!.setDataSource(fd)
            mediaPlayer!!.prepare()
        } catch (e : Exception)
        { Toast.makeText(this,"Bai hat da bi xoa hoac khong ton tai",Toast.LENGTH_LONG).show()}

    }

    public fun receive_Song(): Int {
        listSong = db.getAllSongs() as ArrayList<Song>
        var bundle: Bundle = intent.getBundleExtra("bundle_pos")
        var vt: Int = bundle.getInt("vitri")
        Log.d("rating-old", "" + listSong.get(pos).sRate)
        return vt
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        mediaPlayer!!.seekTo(seekBar!!.progress)
    }

    public fun update() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val time = SimpleDateFormat("mm:ss")
                textView_start.setText(time.format(mediaPlayer!!.getCurrentPosition()))
                seekBar.progress = mediaPlayer!!.getCurrentPosition()
                handler.postDelayed(this, 500)
            }
        }, 100)
    }
    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        var song: Song = listSong.get(pos)
        song.sRate = rating
        db.edit_song(song)
        listSong.set(pos, song)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        var bundle: Bundle = data.getBundleExtra("data")
        var song: Song = listSong.get(pos)
        song.sComment = bundle.getString("comment")
        db.edit_song(song)
        Toast.makeText(this, "Bình luận của bạn đã được cập nhật", Toast.LENGTH_SHORT).show()
    }

}
