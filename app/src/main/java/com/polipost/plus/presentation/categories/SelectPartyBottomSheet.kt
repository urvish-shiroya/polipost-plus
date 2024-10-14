package com.polipost.plus.presentation.categories

import android.content.Context
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polipost.core.extentions.checkStringValue
import com.polipost.core.extentions.gone
import com.polipost.core.extentions.loadImageFromUrl
import com.polipost.core.extentions.setOnSafeClickListener
import com.polipost.core.extentions.visible
import com.polipost.core.network.responses.PreloadResponse
import com.polipost.core.ui.AdaptiveAdapter
import com.polipost.core.ui.BaseBottomSheetDialog
import com.polipost.plus.databinding.BottomsheetSelectPartyBinding
import com.polipost.plus.databinding.ItemFllCategoryBinding
import com.polipost.plus.presentation.home.viewmodel.HomeActivityViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SelectPartyBottomSheet(private val mContext: Context) :
    BaseBottomSheetDialog<BottomsheetSelectPartyBinding>() {

    override fun getViewBinding(): BottomsheetSelectPartyBinding =
        BottomsheetSelectPartyBinding.inflate(layoutInflater)

    private val mViewModel: HomeActivityViewModel by activityViewModel()

    private var mDataList: List<PreloadResponse.FLL>? = null
    private var adaptiveAdapter: AdaptiveAdapter<PreloadResponse.FLL, ItemFllCategoryBinding>? = null
    private var selectedItemId: String = ""

    override fun contentArea() {
        mBinding?.errorContainer?.gone()
        initializeClickListener()
        attachObserver()
    }

    private fun initializeClickListener() {
        mBinding?.searchContainer?.doAfterTextChanged { text ->
            val filterList = mDataList?.filter { it.categoryName.lowercase().contains(text.toString().lowercase()) }
            adaptiveAdapter?.submitList(filterList)

            if (!filterList.isNullOrEmpty()) {
                mBinding?.recyclerView?.visible()
                mBinding?.errorContainer?.gone()
            } else {
                mBinding?.errorContainer?.displayError()
                mBinding?.recyclerView?.gone()
                mBinding?.errorContainer?.visible()
            }
        }
    }

    private fun attachObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.fllCategoryList.collect {
                    mDataList = it
                    initializeAdapterIfRequire()
                }
            }
        }
        mViewModel.getPoliticalFllCategoryFromPreference()
    }

    private val diffCallback = object : DiffUtil.ItemCallback<PreloadResponse.FLL>() {
        override fun areItemsTheSame(oldItem: PreloadResponse.FLL, newItem: PreloadResponse.FLL): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PreloadResponse.FLL, newItem: PreloadResponse.FLL): Boolean {
            return oldItem == newItem
        }
    }

    private fun initializeAdapterIfRequire() {
        if (adaptiveAdapter == null) {
            adaptiveAdapter = AdaptiveAdapter(
                bindingInflater = ItemFllCategoryBinding::inflate,
                bindView = { itemBinding, currentItem, _ ->
                    if (selectedItemId == currentItem.id) {
                        Log.e("@@@", "initializeAdapterIfRequire: ${currentItem.categoryName}" )
                    }
                    itemBinding.categoryName.text = currentItem.categoryName.toString()
                    itemBinding.imageIcon.loadImageFromUrl(currentItem.logos.firstOrNull())
                    itemBinding.root.setOnSafeClickListener {
                        selectedItemId = currentItem.id
                        adaptiveAdapter?.notifyDataSetChanged()
                    }
                },
                diffCallback = diffCallback
            )

            mBinding?.recyclerView?.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
            mBinding?.recyclerView?.adapter = adaptiveAdapter
        }

        adaptiveAdapter?.submitList(mDataList ?: listOf())
    }
}