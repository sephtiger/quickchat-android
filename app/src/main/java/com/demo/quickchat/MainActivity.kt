package com.demo.quickchat

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.FirebaseApp
import com.quickchat.QuickChat
import com.quickchat.data.interactor.Result
import com.quickchat.data.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var me = User()
//        me.id = "new-user-id"
//        me.name = "New User"
//        me.id = "my-test-id"
///        me.name = "Test"
        me.id = "my-test-unread-count-id-3"
        me.name = "Test Unread Count 3"
//        me.id = "third-property-id"
//        me.name = "Property Three"
        QuickChat.initialize(me)
        fab.setOnClickListener { _ ->
            fab.hide()
            var property = User()
            property.id = "third-property-id"
            property.name = "Property Three"

            QuickChat.getInstance().createChannel(property.name, property)
                .subscribeOn(Schedulers.io()) // run on seperate thread
                .observeOn(AndroidSchedulers.mainThread()) // go back to UI thread
                .subscribe(Consumer{
                    fab.show()
                    when(it) {
                        is Result.Success<*> -> {
                            Snackbar.make(fab, "Success ${it.data}", Snackbar.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            Snackbar.make(fab, "Error ${it.message}", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
