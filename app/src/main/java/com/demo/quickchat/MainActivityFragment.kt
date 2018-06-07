package com.demo.quickchat

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.quickchat.QuickChat
import com.quickchat.data.interactor.Result
import com.quickchat.data.model.Channel
import io.reactivex.disposables.Disposable

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), ChannelsAdapter.OnItemClickedListener {

    val channelsAdapter = ChannelsAdapter(this)

    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        var recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView

        with(recyclerView) {
            adapter = channelsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        disposable = QuickChat.getInstance().getChannels().subscribe({
            when(it) {
                is Result.Success<*> -> channelsAdapter.setCities(it.data as List<Channel>)
                is Result.Error -> Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onItemClicked(channel: Channel) {
        var intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("channel", channel)
        startActivity(intent)
    }
}
