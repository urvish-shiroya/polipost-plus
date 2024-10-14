package com.polipost.core.network.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HomeResponse(
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("main_category") val mainCategory: String = "",
    @field:SerializedName("image") val image: String = "",
    @field:SerializedName("template_id") val templateId: String = "",
    @field:SerializedName("type") val type: String = "free",
)