package com.example.vietpc.musicplayer.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.vietpc.musicplayer.*
import kotlinx.android.synthetic.main.music_item.view.*
import java.sql.Time
import java.util.concurrent.TimeUnit

class SongAdapter(ListSong: ArrayList<Song>,context : Context):RecyclerView.Adapter<SongAdapter.viewHolder>() {
    var contextt = context
    var listSong = ListSong
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.music_item,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var BaiHat = listSong[position]
        var sName = BaiHat.sName
        var sTime  = MandT(BaiHat.sTime.toLong())
        holder!!.Name_song.text = sName
        holder.Time_Song.text = sTime
        holder.SetOnClickListener(object: ItemClick{
            override fun oniTemClick(view: View, vitri: Int) {
                var intent : Intent = Intent(contextt,PlayerActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                var bundle : Bundle = Bundle()
                bundle.putInt("vitri",vitri)
                intent.putExtra("bundle_pos",bundle)
                contextt.startActivity(intent)
            }
        })
    }
    fun MandT(giay: Long): String{
        var time = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(giay),
                                              TimeUnit.MILLISECONDS.toSeconds(giay)-TimeUnit.MINUTES.toSeconds(
                                                      TimeUnit.MILLISECONDS.toMinutes(giay)))
        return time
    }
    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var Image_song : ImageView
        var Name_song : TextView
        var Time_Song : TextView
        var itemClick:ItemClick?=null
        init{
            Image_song = itemView.findViewById(R.id.image_music_item)
            Name_song = itemView.findViewById(R.id.text_view_name_song)
            Time_Song = itemView.findViewById(R.id.text_view_time)
            itemView.setOnClickListener(this)
        }
        fun SetOnClickListener(clickitem: ItemClick){
            this.itemClick = clickitem
        }

        override fun onClick(v: View?) {
            this.itemClick!!.oniTemClick(v!!,adapterPosition)
        }
    }
}