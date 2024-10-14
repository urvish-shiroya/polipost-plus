package com.polipost.plus.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.polipost.core.extentions.backgroundScope
import com.polipost.core.extentions.checkStringValue
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.isNetworkConnected
import com.polipost.core.network.ApiServices
import com.polipost.core.network.NetworkResponse
import com.polipost.core.network.responses.HomeResponse
import com.polipost.core.store.preference.userId
import com.polipost.core.ui.BaseViewModel
import kotlinx.coroutines.launch

class HomeChildFragmentViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    private var _homeTemplates = MutableLiveData<NetworkResponse<List<HomeResponse>>>(NetworkResponse.Default())
    internal var homeTemplates: LiveData<NetworkResponse<List<HomeResponse>>> = _homeTemplates

    fun fetchHomeTemplates(mContext: Context, categoryId: String) {
        if (mContext.isNetworkConnected()) {
            backgroundScope.launch {
                _homeTemplates.postValue(NetworkResponse.Loading())
                runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId

                    if (checkStringValue(categoryId)) {
                        mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_category_id)] = categoryId
                        apiServices.fetchHomeTemplatesWithCategoryId(mParams)
                    } else {
                        apiServices.fetchHomeTemplates(mParams)
                        listOf<HomeResponse>()
                    }
                }.fold({
                    _homeTemplates.postValue(NetworkResponse.Success(it))
                }, {
                    _homeTemplates.postValue(NetworkResponse.Failure(it))
                })
            }
        } else {
            _homeTemplates.postValue(NetworkResponse.Failure(Throwable(mContext.getCompatString(com.polipost.core.R.string.no_internet_connection))))
        }
    }

}