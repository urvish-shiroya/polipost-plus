package com.polipost.core.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import com.polipost.core.R
import com.polipost.core.extentions.getCompatString
import com.polipost.core.extentions.visible
import com.polipost.core.extentions.visibleIf
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorContainer : ConstraintLayout {

    private var iconView: AppCompatImageView? = null
    private var titleTextView: AppCompatTextView? = null
    private var descriptionTextView: AppCompatTextView? = null
    private var actionButton: AppCompatTextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.error_container, this, true)
        iconView = findViewById(R.id.iconImage)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        actionButton = findViewById(R.id.actionButton)
    }

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(mContext: Context, attrs: AttributeSet? = null) {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ErrorContainer, 0, 0)

        try {
            val isIconVisible = mTypedArray.getBoolean(R.styleable.ErrorContainer_isIconVisible, true)
            val isButtonVisible = mTypedArray.getBoolean(R.styleable.ErrorContainer_isButtonVisible, true)

            iconView?.visibleIf(isIconVisible)
            actionButton?.visibleIf(isButtonVisible)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mTypedArray.recycle()
        }
    }

    fun displayError(throwable: Throwable? = null) {
        when (throwable) {
            is UnknownHostException -> {
                iconView?.updatePadding(10, 10, 10, 10)
                iconView?.setImageResource(R.drawable.icon_no_internet)
                titleTextView?.text = context.getCompatString(R.string.error_title_no_internet_connection)
                descriptionTextView?.text = context.getCompatString(R.string.error_content_no_internet_connection)
                actionButton?.text = context.getCompatString(R.string.try_again)
            }

            else -> {
                iconView?.updatePadding(10, 10, 10, 10)
                iconView?.setImageResource(R.drawable.icon_server_error)
                titleTextView?.text = context.getCompatString(R.string.error_title_server_error)
                descriptionTextView?.text = context.getCompatString(R.string.error_content_server_error)
                actionButton?.text = context.getCompatString(R.string.try_again)
            }
        }
    }

    fun setOnActionClickListener(listener: OnClickListener) {
        if (actionButton?.hasOnClickListeners() == false) {
            actionButton?.setOnClickListener(listener)
        }
    }

}