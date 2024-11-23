package com.polipost.plus.presentation.profile.ui

import android.os.Bundle
import android.util.Log
import com.polipost.core.extentions.asFile
import com.polipost.core.extentions.checkIsExists
import com.polipost.core.extentions.getProfilesDirectory
import com.polipost.core.extentions.ifFalse
import com.polipost.core.ui.BaseActivity
import com.polipost.plus.databinding.ActivityUserProfileBinding

class UserProfileActivity : BaseActivity<ActivityUserProfileBinding>() {
    override fun getViewBinding(): ActivityUserProfileBinding =
        ActivityUserProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeView()
        initializeClickListener()
        attachObserver()
    }

    private fun initializeView() {

    }

    private fun initializeClickListener() {

    }

    private fun attachObserver() {

    }
}