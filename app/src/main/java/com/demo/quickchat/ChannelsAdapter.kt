package com.demo.quickchat;

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.quickchat.data.model.Channel
import java.util.*

/**
 * Created by seph on 24/05/2018.
 */
class ChannelsAdapter(private val onItemClickedListener: OnItemClickedListener): RecyclerView.Adapter<ChannelsAdapter.MyViewHolder>() {

    val channelList: ArrayList<Channel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return channelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.setOnClickListener{
            onItemClickedListener.onItemClicked(channelList[position])
        }
        holder.textView?.text = channelList[position].name
    }

    fun setCities(channelList: List<Channel>) {
        this.channelList.clear()
        this.channelList.addAll(channelList)
        notifyDataSetChanged()
    }

    class MyViewHolder: RecyclerView.ViewHolder {

        var textView: TextView? = null

        constructor(itemView: View) : super(itemView) {
            textView = itemView.findViewById(android.R.id.text1)
        }
    }

    interface OnItemClickedListener {

        fun onItemClicked(channel: Channel)

    }
}