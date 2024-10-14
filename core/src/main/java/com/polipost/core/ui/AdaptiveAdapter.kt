package com.polipost.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class AdaptiveAdapter<T, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindView: (VB, currentItem: T, position: Int) -> Unit,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, AdaptiveAdapter.AdaptiveViewHolder<VB>>(diffCallback) {

    class AdaptiveViewHolder<VB : ViewBinding>(val mBinding: VB) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptiveViewHolder<VB> {
        val binding = bindingInflater(LayoutInflater.from(parent.context), parent, false)
        return AdaptiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdaptiveViewHolder<VB>, position: Int) {
        bindView(holder.mBinding, currentList[position], position)
    }
}