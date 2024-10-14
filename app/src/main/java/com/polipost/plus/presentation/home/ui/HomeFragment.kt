package com.polipost.plus.presentation.home.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.polipost.core.network.NetworkResponse
import com.polipost.core.network.responses.HomeCategoryResponse
import com.polipost.core.ui.BaseFragment
import com.polipost.plus.databinding.FragmentHomeBinding
import com.polipost.plus.presentation.home.adapters.HomePagerAdapter
import com.polipost.plus.presentation.home.viewmodel.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

    private val mViewModel: HomeFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initializeClickListener()
        attachObserver()
    }

    private fun initializeView() {

    }

    private fun initializeClickListener() {

    }

    private fun attachObserver() {
        mViewModel.fetchHomeCategory(requireContext())

        mViewModel.homeCategory.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Default -> {}
                is NetworkResponse.Failure -> {}
                is NetworkResponse.Loading -> {}
                is NetworkResponse.Success -> {
                    initializeTabLayouts(it.data)
                }
            }
        }
    }

    private fun initializeTabLayouts(data: List<HomeCategoryResponse>?) {

        val pagerAdapter = HomePagerAdapter(this, data)
        mBinding?.viewPager?.adapter = pagerAdapter

        TabLayoutMediator(mBinding?.tabLayout!!, mBinding?.viewPager!!) { tab, position ->
            tab.text = data?.get(position)?.name.toString()
        }.attach()
    }

}