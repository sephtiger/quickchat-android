package com.demo.quickchat

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.quickchat.QuickChat
import com.quickchat.data.interactor.Result
import com.quickchat.data.model.Channel
import com.quickchat.data.model.Message
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class ChatActivity : AppCompatActivity() {

    val messagesAdapter = MessagesAdapter()

    var channel: Channel? = null

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        channel = intent.getParcelableExtra("channel")

        var recyclerView = findViewById(R.id.recyclerview) as RecyclerView

        with(recyclerView) {
            adapter = messagesAdapter
            layoutManager = LinearLayoutManager(context)
            scrollToPosition(adapter.itemCount)
        }

        var text = findViewById(R.id.text) as EditText
        var button = findViewById(R.id.button) as Button

        button.setOnClickListener {

            var message = text.text.toString()

            if(!message.isEmpty()) {
                QuickChat.getInstance().sendMessage(channel?.id!!, message)
                        .subscribe(Consumer {
                            when (it) {
                                is Result.Success<*> -> {
                                    Toast.makeText(baseContext, "Sent", Toast.LENGTH_SHORT).show()
                                }
                                is Result.Error -> {
                                    Toast.makeText(baseContext, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })

                text.text.clear()
                var imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(text.getWindowToken(), 0)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        QuickChat.getInstance().setChannelOpen(channel?.id!!, true).subscribe()
        disposable = QuickChat.getInstance().getMessages(channel?.id!!).subscribe({
            when(it) {
                is Result.Success<*> -> messagesAdapter.setMessages(it.data as List<Message>)
                is Result.Error -> Toast.makeText(baseContext, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
        QuickChat.getInstance().setChannelOpen(channel?.id!!, false).subscribe()
    }
}
