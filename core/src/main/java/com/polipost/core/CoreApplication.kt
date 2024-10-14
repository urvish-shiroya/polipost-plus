package com.polipost.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class CoreApplication : Application() {

    abstract fun getKoinModules(): List<Module>

    companion object {
        private var mInstance: CoreApplication? = null

        fun getCoreInstance() = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        initializationKoin()
    }

    private fun initializationKoin() {
        val anotherModules = getKoinModules()

        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)
            androidFileProperties()
            modules(anotherModules)
        }
    }
}