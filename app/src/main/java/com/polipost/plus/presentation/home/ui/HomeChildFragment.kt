package com.polipost.plus.presentation.home.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polipost.core.extentions.gone
import com.polipost.core.extentions.showToast
import com.polipost.core.extentions.visible
import com.polipost.core.network.NetworkResponse
import com.polipost.core.network.responses.HomeResponse
import com.polipost.core.ui.BaseFragment
import com.polipost.plus.databinding.FragmentHomeChildBinding
import com.polipost.plus.presentation.home.adapters.HomeAdapter
import com.polipost.plus.presentation.home.viewmodel.HomeChildFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeChildFragment : BaseFragment<FragmentHomeChildBinding>() {

    override fun getViewBinding(): FragmentHomeChildBinding =
        FragmentHomeChildBinding.inflate(layoutInflater)

    private val mViewModel: HomeChildFragmentViewModel by viewModel()

    private var categoryId: String = ""
    private var mHomeAdapter: HomeAdapter? = null
    private var mLayoutManager: GridLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = if (arguments?.containsKey("category_id") == true) {
            arguments?.getString("category_id").toString()
        } else {
            ""
        }


        initializeView()
        initializeClickListener()
        attachObserver()
    }

    private fun initializeView() {

    }

    private fun initializeClickListener() {

    }

    private fun attachObserver() {
        mViewModel.fetchHomeTemplates(requireContext(), categoryId)

        mViewModel.homeTemplates.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Default -> {}

                is NetworkResponse.Failure -> {
                    context?.showToast(it.error?.message)
                    mBinding?.recyclerView?.gone()
                    mBinding?.shimmerLayout?.gone()
                    mBinding?.shimmerLayout?.stopShimmer()
                }

                is NetworkResponse.Loading -> {
                    mBinding?.recyclerView?.gone()
                    mBinding?.shimmerLayout?.visible()
                    mBinding?.shimmerLayout?.startShimmer()
                }

                is NetworkResponse.Success -> {
                    mBinding?.recyclerView?.visible()
                    mBinding?.shimmerLayout?.gone()
                    mBinding?.shimmerLayout?.stopShimmer()
                    initializeRecyclerView(it.data)
                }
            }
        }
    }

    private fun initializeRecyclerView(mDataList: List<HomeResponse>?) {
        if (mHomeAdapter == null) {
            mLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            mHomeAdapter = HomeAdapter(requireContext(), mDataList)
            mBinding?.recyclerView?.layoutManager = mLayoutManager
            mBinding?.recyclerView?.adapter = mHomeAdapter
        }
    }

}