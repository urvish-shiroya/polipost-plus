package com.polipost.plus.presentation.categories

import android.content.Context
import com.polipost.core.ui.BaseAppDialog
import com.polipost.plus.databinding.DialogCategoriesBinding

class CategoryDialog(private val mContext: Context) : BaseAppDialog<DialogCategoriesBinding>(mContext) {

    override fun getViewBinding(): DialogCategoriesBinding =
        DialogCategoriesBinding.inflate(layoutInflater)

    override fun contentArea() {

    }
}