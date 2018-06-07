package com.quickchat.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.quickchat.data.model.base.BaseModel
import java.util.*

/**
 * Created by seph on 23/05/2018.
 */

class Channel constructor() : BaseModel(), Parcelable {

    override
    var id: String = ""

    override
    var name: String = ""

    @get:PropertyName("unread_count")
    @set:PropertyName("unread_count")
    var unreadCount: Int? = null

    @get:PropertyName("last_message")
    @set:PropertyName("last_message")
    var lastMessage: Message? = null

    @get:PropertyName("updated_at")
    @set:PropertyName("updated_at")
    var updatedAt: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        unreadCount = parcel.readInt()
        lastMessage = parcel.readParcelable(Message::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(unreadCount!!)
        parcel.writeParcelable(lastMessage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Channel && id == other?.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}