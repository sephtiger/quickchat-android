package com.demo.quickchat;

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quickchat.data.model.Message


/**
 * Created by seph on 24/05/2018.
 */
class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MyViewHolder>() {

    val messageList: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = messageList.get(position).content
    }

    fun setMessages(messageList: List<Message>) {
        this.messageList.clear()
        this.messageList.addAll(messageList)
        notifyDataSetChanged()
    }

    class MyViewHolder: RecyclerView.ViewHolder {

        var textView: TextView? = null

        constructor(itemView: View) : super(itemView) {
            textView = itemView.findViewById(android.R.id.text1)
        }
    }
}