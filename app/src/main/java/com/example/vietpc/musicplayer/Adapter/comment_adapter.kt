package com.example.vietpc.musicplayer.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vietpc.musicplayer.R

class comment_adapter(private val context: Context, private var contactList: List<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return contactList!!.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (view == null) {
            view = inflater.inflate(R.layout.comment_item, null)
            holder = ViewHolder()
            holder.txtnguoinghe = view!!.findViewById(R.id.textView_nguoinghe) as TextView
            holder.txtcomment = view!!.findViewById(R.id.editText_Show_cmt) as EditText
            holder.txtnguoinghe!!.setText("Đánh giá " + i + ":")
            holder.txtcomment!!.setText(contactList!!.get(i))

            view.tag = holder
        } else
        {  holder = view.tag as ViewHolder }

        holder.txtnguoinghe!!.setText("Đánh giá " + i + ":")
        holder.txtcomment!!.setText(contactList!!.get(i))
        return view
    }

    private inner class ViewHolder {
        var txtnguoinghe: TextView? = null
        var txtcomment: EditText? = null
    }
}