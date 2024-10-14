package com.polipost.plus.presentation.home.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.polipost.core.extentions.changeStatusBarColor
import com.polipost.core.extentions.launchActivity
import com.polipost.core.extentions.setOnSafeClickListener
import com.polipost.core.ui.BaseActivity
import com.polipost.plus.databinding.ActivityHomeBinding
import com.polipost.plus.presentation.categories.SelectPartyBottomSheet
import com.polipost.plus.presentation.home.viewmodel.HomeActivityViewModel
import com.polipost.plus.presentation.settings.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    private val mViewModel: HomeActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding?.root)
        changeStatusBarColor(com.polipost.core.R.color.toolbar_background)

        initializeView()
        initializeClickListener()
        attachObserver()
        loadFragment(HomeFragment(), "HomeFragment")
    }

    private fun initializeView() {
        mViewModel.preloadData(this)
    }

    private fun initializeClickListener() {
        mBinding?.apply {
            imageSetting.setOnSafeClickListener {
                launchActivity<SettingActivity> { }
            }

            imageSearch.setOnSafeClickListener {
//                CategoryDialog(this@HomeActivity).show()
                showPartyBottomSheet()
            }
        }
    }

    private fun attachObserver() {

    }

    private fun loadFragment(mFragment: Fragment, tag: String) {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val existingFragment = mFragmentManager.findFragmentByTag(tag)

        if (existingFragment != null) {
            mFragmentTransaction.show(existingFragment)
        } else {
            mFragmentTransaction.add(mBinding?.fragmentContainer!!, mFragment, tag);
        }

        mFragmentManager.fragments.forEach {
            if (it != null && it == existingFragment) {
                mFragmentTransaction.hide(it)
            }
        }
        mFragmentTransaction.commit()
    }

    private fun showPartyBottomSheet() {
        SelectPartyBottomSheet(this).show(supportFragmentManager, "SelectPartyBottomSheet")
    }
}