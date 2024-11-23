package com.polipost.plus.presentation.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polipost.core.extentions.asFile
import com.polipost.core.extentions.backgroundScope
import com.polipost.core.extentions.downloadImageFromUrl
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.getProfilesDirectory
import com.polipost.core.extentions.isNetworkConnected
import com.polipost.core.network.ApiServices
import com.polipost.core.network.NetworkResponse
import com.polipost.core.network.responses.GetUserProfileResponse
import com.polipost.core.store.preference.userId
import com.polipost.core.ui.BaseViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SplashViewModel(private val apiServices: ApiServices) : BaseViewModel() {

    private var _userPackages = MutableLiveData<NetworkResponse<Any>>(NetworkResponse.Default())
    internal val userPackages: LiveData<NetworkResponse<Any>> = _userPackages

    private var _userProfiles = MutableLiveData<NetworkResponse<GetUserProfileResponse>>(NetworkResponse.Default())
    internal val userProfiles: LiveData<NetworkResponse<GetUserProfileResponse>> = _userProfiles

    fun getUserPackage(mContext: Context) {
        backgroundScope.launch {
            if (mContext.isNetworkConnected()) {
                _userPackages.postValue(NetworkResponse.Loading())
                runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId
                    apiServices.getUserPackages(mParams)
                }.fold({
                    _userPackages.postValue(NetworkResponse.Success(it))
                }, {
                    _userPackages.postValue(NetworkResponse.Failure(it))
                })
            } else {
                _userPackages.postValue(NetworkResponse.Failure(UnknownHostException(mContext.getCompatString(com.polipost.core.R.string.no_internet_connection))))
            }
        }
    }

    fun getUserProfile(mContext: Context) {
        backgroundScope.launch {
            if (mContext.isNetworkConnected()) {
                _userPackages.postValue(NetworkResponse.Loading())
                kotlin.runCatching {
                    val mParams = mutableMapOf<String, Any>()
                    mParams[mContext.getCompatString(com.polipost.core.R.string.api_key_user_id)] = mContext.userId
                    apiServices.getUserProfile(mParams)
                }.fold({ response ->
                    mContext.downloadImageFromUrl(
                        imageUrl = response.userProfiles?.firstOrNull()?.profilePicture01,
                        outputFile = (mContext.getProfilesDirectory().absolutePath + "/profile_01.png").asFile(),
                        callback = { result ->
                            result.onSuccess {
                                _userProfiles.postValue(NetworkResponse.Success(response))
                            }
                        }
                    )
                }, {
                    _userPackages.postValue(NetworkResponse.Failure(it))
                })
            } else {
                _userPackages.postValue(NetworkResponse.Failure(UnknownHostException(mContext.getCompatString(com.polipost.core.R.string.no_internet_connection))))
            }
        }
    }
}