package com.polipost.core.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.polipost.core.R

abstract class BaseAppDialog<T : ViewBinding?>(private val mContext: Context) : Dialog(mContext, R.style.applicationDialog) {

    protected var mBinding: T? = null

    abstract fun getViewBinding(): T

    abstract fun contentArea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewBinding()
        if (mBinding == null) return
        setContentView(mBinding!!.root)

        contentArea()
    }
}