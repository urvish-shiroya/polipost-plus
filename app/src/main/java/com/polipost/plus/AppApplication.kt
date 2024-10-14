package com.polipost.plus

import com.polipost.core.CoreApplication
import com.polipost.core.di.databaseModule
import com.polipost.core.di.networkModule
import com.polipost.plus.di.viewModelModule
import org.koin.core.module.Module

class AppApplication : CoreApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getKoinModules(): List<Module> {
        return listOf(
            viewModelModule,
            databaseModule,
            networkModule
        )
    }
}