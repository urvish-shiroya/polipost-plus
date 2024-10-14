package com.polipost.core.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polipost.core.R
import com.polipost.core.extentions.getDeviceHeight

abstract class BaseBottomSheetDialog<T : ViewBinding?> : BottomSheetDialogFragment() {

    protected var mBinding: T? = null

    abstract fun getViewBinding(): T

    abstract fun contentArea()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.application_bottom_sheet)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = getViewBinding()
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutParams = mBinding?.root?.layoutParams
        layoutParams?.height = (context.getDeviceHeight() * 0.75).toInt()
        mBinding?.root?.layoutParams = layoutParams
        val bottomSheet = dialog as? BottomSheetDialog
        bottomSheet?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        contentArea()
    }
}