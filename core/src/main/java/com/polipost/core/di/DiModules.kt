package com.polipost.core.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.polipost.core.network.ApiServices
import com.polipost.core.store.database.AppDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single { createDatabase(androidContext(), getProperty("database_name")) }
}

val networkModule = module {
    single { buildRetrofit(getProperty("server_url")) }

    single { createWebService(get()) }
}


//------------------------------------------ Functions ------------------------------------------//
private fun createDatabase(mContext: Context, databaseName: String): AppDatabase {
    return Room.databaseBuilder(
        mContext,
        AppDatabase::class.java,
        databaseName
    ).build()
}

private fun buildRetrofit(serverUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .client(OkHttpClient.Builder().build())
        .build()
}

private fun createWebService(retrofit: Retrofit): ApiServices {
    return retrofit.create(ApiServices::class.java)
}