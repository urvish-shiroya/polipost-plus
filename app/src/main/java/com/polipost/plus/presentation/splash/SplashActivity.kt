package com.polipost.plus.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.polipost.core.extentions.gone
import com.polipost.core.extentions.launchActivity
import com.polipost.core.extentions.setSystemBarInsets
import com.polipost.core.extentions.showToast
import com.polipost.core.extentions.visible
import com.polipost.core.network.NetworkResponse
import com.polipost.core.ui.BaseActivity
import com.polipost.plus.databinding.ActivitySplashBinding
import com.polipost.plus.presentation.home.ui.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    private val mViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding?.root)
        setSystemBarInsets(mBinding!!.root)

        attachObserver()
    }

    private fun attachObserver() {
        mViewModel.userPackages.observe(this) { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.Default -> {}

                is NetworkResponse.Loading -> {
                    mBinding?.errorContainer?.gone()
                }

                is NetworkResponse.Success -> {
                    mBinding?.errorContainer?.gone()
                    launchActivity<HomeActivity> { }
                    finish()
                }

                is NetworkResponse.Failure -> {
                    mBinding?.errorContainer?.displayError(networkResponse.error)
                    mBinding?.errorContainer?.setOnActionClickListener {
                        mViewModel.getUserPackage(this)
                    }
                    mBinding?.errorContainer?.visible()
                    showToast(networkResponse.error?.message.toString())
                }
            }
        }

        mViewModel.getUserPackage(this)
    }
}