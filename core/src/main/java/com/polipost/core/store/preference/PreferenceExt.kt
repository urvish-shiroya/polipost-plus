package com.polipost.core.store.preference

import android.content.Context
import com.polipost.core.R
import com.polipost.core.extentions.getCompatString

var Context.userId: String
    get() = PreferenceHelper[getCompatString(R.string.pref_key_user_id), "150"]
    set(value) = kotlin.run { PreferenceHelper[getCompatString(R.string.pref_key_user_id)] = value }