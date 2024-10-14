package com.polipost.plus.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polipost.core.extentions.loadImageFromUrl
import com.polipost.core.extentions.visibleIf
import com.polipost.core.network.responses.HomeResponse
import com.polipost.plus.databinding.ItemHomeTemplateBinding

class HomeAdapter(
    private val mContext: Context,
    private val mDataList: List<HomeResponse>? = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(ItemHomeTemplateBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun getItemCount(): Int = mDataList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeViewHolder) (holder as HomeViewHolder).onBind(mDataList?.get(position))
    }

    inner class HomeViewHolder(private val mBinding: ItemHomeTemplateBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun onBind(currentData: HomeResponse?) {
            currentData?.let {
                mBinding.imageTemplate.loadImageFromUrl(it.image, com.polipost.core.R.drawable.ic_place_holder_6_7)

                mBinding.imagePremium.visibleIf(it.type == "Paid")
            }
        }
    }
}