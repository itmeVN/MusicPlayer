package com.example.vietpc.musicplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.vietpc.musicplayer.Adapter.comment_adapter
import kotlinx.android.synthetic.main.activity_comment_act.*

class comment_act : AppCompatActivity() {
    var adapter: comment_adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_act)
        var bundle: Bundle = this.intent.extras
        var cmt: String = bundle.getString("comment")
        var listcmt: List<String> = cmt.split("|")
        adapter = comment_adapter(this, listcmt)
        ListView_cmt.adapter = adapter
        button_CN.setOnClickListener {
            var inttent: Intent = Intent(this, PlayerActivity::class.java)
            bundle = Bundle()
            bundle.putString("comment", cmt + "|" + editText_commnet.text.toString())
            inttent.putExtra("data", bundle)
            this.setResult(100, inttent)
            finish()
        }
        button_Thoat.setOnClickListener {
            finish()
        }
    }
}

