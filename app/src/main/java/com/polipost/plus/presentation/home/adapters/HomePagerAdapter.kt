package com.polipost.plus.presentation.home.adapters

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.polipost.core.network.responses.HomeCategoryResponse
import com.polipost.plus.presentation.home.ui.HomeChildFragment

class HomePagerAdapter(parentFragment: Fragment, private val dataList: List<HomeCategoryResponse>?) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        val mFragment = HomeChildFragment()
        mFragment.arguments = bundleOf().apply {
            putString("category_id", dataList?.get(position)?.id ?: "")
        }
        return mFragment
    }
}