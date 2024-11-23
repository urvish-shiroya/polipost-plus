package com.polipost.plus.presentation.settings

import android.os.Bundle
import com.polipost.core.extentions.changeStatusBarColor
import com.polipost.core.extentions.launchActivity
import com.polipost.core.extentions.setOnSafeClickListener
import com.polipost.core.network.responses.GetUserProfileResponse
import com.polipost.core.store.preference.PreferenceHelper.getUserProfileFromPreference
import com.polipost.core.ui.BaseActivity
import com.polipost.plus.databinding.ActivitySettingBinding
import com.polipost.plus.presentation.profile.ui.UserProfileActivity

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    private var userProfile: GetUserProfileResponse.Profile? = null

    override fun getViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding?.root)
        changeStatusBarColor(com.polipost.core.R.color.toolbar_background)
        initializeView()
        initializeClickListener()
        attachObserver()
    }

    private fun initializeView() {
        userProfile = getUserProfileFromPreference()

        with(mBinding ?: return) {
            textUserName.text = userProfile?.name.toString()
            textUserEmail.text = userProfile?.email.toString()
        }
    }

    private fun initializeClickListener() {
        mBinding?.apply {
            imageBack.setOnSafeClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            userContainer.setOnSafeClickListener {
                launchActivity<UserProfileActivity> { }
            }
        }
    }

    private fun attachObserver() {

    }

}