package com.polipost.plus.presentation.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polipost.core.extentions.backgroundScope
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.isNetworkConnected
import com.polipost.core.network.ApiServices
import com.polipost.core.network.NetworkResponse
import com.polipost.core.store.preference.userId
import com.polipost.core.ui.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SplashViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    private var _isLoadingSplash: MutableLiveData<Boolean> = MutableLiveData(true)
    internal val isLoadingSplash: LiveData<Boolean> = _isLoadingSplash

    private var _userPackages = MutableLiveData<NetworkResponse<Any>>(NetworkResponse.Default())
    internal val userPackages: LiveData<NetworkResponse<Any>> = _userPackages

    fun getUserPackage(mContext: Context) {
        backgroundScope.launch {
            _isLoadingSplash.postValue(true)
            if (mContext.isNetworkConnected()) {
                _userPackages.postValue(NetworkResponse.Loading())
                runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId
                    apiServices.getUserPackages(mParams)
                }.fold({
                    _userPackages.postValue(NetworkResponse.Success(it))
                    _isLoadingSplash.postValue(false)
                }, {
                    _userPackages.postValue(NetworkResponse.Failure(it))
                    _isLoadingSplash.postValue(false)
                })
            } else {
                _userPackages.postValue(NetworkResponse.Failure(UnknownHostException(mContext.getCompatString(com.polipost.core.R.string.no_internet_connection))))
                _isLoadingSplash.postValue(false)
            }
        }
    }
}