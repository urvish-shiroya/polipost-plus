package com.polipost.core.network.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetUserProfileResponse(
    @field:SerializedName("User_profile") val userProfiles: List<Profile>? = listOf(),
    @field:SerializedName("Business_profile") val businessProfiles: List<Profile>? = listOf(),
) {
    @Keep
    data class Profile(
        @field:SerializedName("id") val id: Int = 0,
        @field:SerializedName("name") val name: String = "",
        @field:SerializedName("email") val email: String = "",
        @field:SerializedName("phone") val phone: String = "",
        @field:SerializedName("address") val address: String = "",
        @field:SerializedName("profile_1") val profilePicture01: String = "",
        @field:SerializedName("profile_2") val profilePicture02: String = "",
        @field:SerializedName("logo_1") val profilePicture03: String = "",
        @field:SerializedName("user_type") val userType: String = "",
    )
}
