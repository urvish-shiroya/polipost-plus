package com.polipost.plus.presentation.home.viewmodel

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.polipost.core.extentions.backgroundScope
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.isNetworkConnected
import com.polipost.core.extentions.toJson
import com.polipost.core.network.ApiServices
import com.polipost.core.network.responses.PreloadResponse
import com.polipost.core.store.preference.PreferenceHelper
import com.polipost.core.store.preference.userId
import com.polipost.core.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeActivityViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    private var _fllCategoryList = MutableStateFlow<List<PreloadResponse.FLL>>(listOf())
    internal val fllCategoryList = _fllCategoryList.asSharedFlow()

    fun preloadData(mContext: Context) {
        backgroundScope.launch {
            if (mContext.isNetworkConnected()) {
                kotlin.runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId
                    apiServices.getPreloadData(mParams)
                }.onSuccess {
                    val convertedJson = it.politicalFllCategoryList.toJson()
                    PreferenceHelper[mContext.getCompatString(com.polipost.core.R.string.pref_key_political_fll_category_list)] = convertedJson
                }
            }
        }
    }

    fun getPoliticalFllCategoryFromPreference(mContext: Context) {
        backgroundScope.launch {
            kotlin.runCatching {
                val preferenceData = PreferenceHelper[mContext.getCompatString(com.polipost.core.R.string.pref_key_political_fll_category_list), ""]
                Gson().fromJson<List<PreloadResponse.FLL>>(preferenceData, object : TypeToken<List<PreloadResponse.FLL>>() {}.type) ?: listOf()
            }.onSuccess {
                _fllCategoryList.tryEmit(it)
            }
        }
    }

}