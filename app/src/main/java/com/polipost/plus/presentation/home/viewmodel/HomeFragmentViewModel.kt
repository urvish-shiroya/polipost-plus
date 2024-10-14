package com.polipost.plus.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.polipost.core.extentions.backgroundScope
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.isNetworkConnected
import com.polipost.core.network.ApiServices
import com.polipost.core.network.NetworkResponse
import com.polipost.core.store.preference.userId
import com.polipost.core.ui.BaseViewModel
import com.polipost.core.network.responses.HomeCategoryResponse
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    private var _homeCategory = MutableLiveData<NetworkResponse<List<HomeCategoryResponse>>>(NetworkResponse.Default())
    internal var homeCategory: LiveData<NetworkResponse<List<HomeCategoryResponse>>> = _homeCategory

    fun fetchHomeCategory(mContext: Context) {
        if (mContext.isNetworkConnected()) {
            backgroundScope.launch {
                _homeCategory.postValue(NetworkResponse.Loading())
                runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_category_type)] = "template"
                    apiServices.fetchHomeCategory(mParams)
                }.fold({
                    _homeCategory.postValue(NetworkResponse.Success(it))
                }, {
                    _homeCategory.postValue(NetworkResponse.Failure(it))
                })
            }
        } else {
            _homeCategory.postValue(NetworkResponse.Failure(Throwable(mContext.getCompatString(com.polipost.core.R.string.no_internet_connection))))
        }
    }
}