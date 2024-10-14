package com.polipost.plus.presentation.settings

import android.os.Bundle
import com.polipost.core.extentions.changeStatusBarColor
import com.polipost.core.ui.BaseActivity
import com.polipost.plus.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

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

    }

    private fun initializeClickListener() {
        mBinding?.apply {
            imageBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun attachObserver() {

    }

}