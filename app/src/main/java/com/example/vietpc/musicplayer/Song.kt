package com.example.vietpc.musicplayer

import java.io.Serializable

class Song(song_name: String, song_time: String,rate : Float, cmt : String, path : String ) : Serializable {
    var sName = song_name
    var sTime = song_time
    var sRate: Float? = rate
    var sComment: String = cmt
    var sPath : String = path
    var sbyte : Byte? = null
}