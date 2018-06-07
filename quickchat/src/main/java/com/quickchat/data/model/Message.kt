package com.quickchat.data.model;

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import java.util.*

/**
 * Created by seph on 24/05/2018.
 */
class Message() : Parcelable {

    var id: String = ""

    var type: String = ""

    var content: String = ""

    var sender: User? = null

    @get:PropertyName("created_at")
    @set:PropertyName("created_at")
    var createdAt: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        type = parcel.readString()
        content = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Message && id == other?.id
    }
}
