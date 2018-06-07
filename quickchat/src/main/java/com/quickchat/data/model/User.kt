package com.quickchat.data.model

import com.quickchat.data.model.base.BaseModel

/**
 * Created by seph on 23/05/2018.
 */

class User : BaseModel() {

    override
    var id: String = ""

    override
    var name: String = ""

    override fun equals(other: Any?): Boolean {
        return other is User && id == other?.id
    }
}