package com.polipost.core.store.preference

import android.content.Context
import android.content.SharedPreferences
import com.polipost.core.CoreApplication
import com.polipost.core.R
import com.polipost.core.extentions.convertTo
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.ifNotNull
import com.polipost.core.extentions.isNotNull
import com.polipost.core.extentions.toJson
import com.polipost.core.network.responses.GetUserProfileResponse

object PreferenceHelper {
    private const val PREFERENCE_NAME = "polipost"
    private var mCacheMap: MutableMap<String, Any?> = mutableMapOf()

    fun getCustomSharedPreference(): SharedPreferences? =
        CoreApplication.getCoreInstance()?.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun set(key: String, value: Any?) {
        getCustomSharedPreference()?.let { store ->
            when (value) {
                is String? -> store.edit { it.putString(key, value) }
                is Int -> store.edit { it.putInt(key, value) }
                is Boolean -> store.edit { it.putBoolean(key, value) }
                is Float -> store.edit { it.putFloat(key, value) }
                is Long -> store.edit { it.putLong(key, value) }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
    }


    inline operator fun <reified T : Any> get(key: String, defaultValue: T): T {
        getCustomSharedPreference()?.let { store ->
            return when (T::class) {
                String::class -> store.getString(key, defaultValue as? String ?: "") as T
                Int::class -> store.getInt(key, defaultValue as? Int ?: -1) as T
                Boolean::class -> store.getBoolean(key, defaultValue as? Boolean ?: false) as T
                Float::class -> store.getFloat(key, defaultValue as? Float ?: -1f) as T
                Long::class -> store.getLong(key, defaultValue as? Long ?: -1) as T
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        } ?: throw NullPointerException("Preference Was null instance")
    }

    fun Context.setUserProfileInPreference(profile: GetUserProfileResponse.Profile?) {
        if (profile.isNotNull()) {
            mCacheMap[getCompatString(R.string.pref_key_user_profile)] = profile
            set(getCompatString(R.string.pref_key_user_profile), profile.toJson())
        }
    }

    fun Context.getUserProfileFromPreference(): GetUserProfileResponse.Profile? {
        return if (mCacheMap.containsKey(getCompatString(R.string.pref_key_user_profile)) && mCacheMap[getCompatString(R.string.pref_key_user_profile)] != null) {
            mCacheMap[getCompatString(R.string.pref_key_user_profile)] as? GetUserProfileResponse.Profile?
        } else {
            val storedData = get(getCompatString(R.string.pref_key_user_profile), "").convertTo<GetUserProfileResponse.Profile>()
            mCacheMap[getCompatString(R.string.pref_key_user_profile)] = storedData
            storedData
        }
    }
}