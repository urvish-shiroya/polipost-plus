package com.polipost.core.network

import com.google.gson.JsonElement
import com.polipost.core.network.responses.HomeCategoryResponse
import com.polipost.core.network.responses.HomeResponse
import com.polipost.core.network.responses.PreloadResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @POST("Categoriesdata")
    suspend fun fetchHomeCategory(@Body mParams: MutableMap<String, Any>): List<HomeCategoryResponse>

    @POST("NewstTemplate2")
    suspend fun fetchHomeTemplates(@Body mParams: MutableMap<String, Any>): JsonElement

    @POST("template_category")
    suspend fun fetchHomeTemplatesWithCategoryId(@Body mParams: MutableMap<String, Any>): List<HomeResponse>

    @POST("userpackage")
    suspend fun getUserPackages(@Body mParams: MutableMap<String, Any>): JsonElement

    @POST("preload")
    suspend fun getPreloadData(@Body mParams: MutableMap<String, Any>): PreloadResponse

}