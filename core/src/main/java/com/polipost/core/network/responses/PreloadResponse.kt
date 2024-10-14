package com.polipost.core.network.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PreloadResponse(
    @field:SerializedName("Political_Fll") val politicalFllCategoryList: List<FLL> = listOf(),
) {
    @Keep
    data class FLL(
        @field:SerializedName("id") val id: String = "",
        @field:SerializedName("fll_category_name") val categoryName: String = "",
        @field:SerializedName("fll_logo") val logos: List<String> = listOf(),
    )
}