package com.chain.githubissues

import android.app.Application
import com.chain.githubissues.di.ApplicationComponent
import com.chain.githubissues.di.ContextModule
import com.chain.githubissues.di.DaggerApplicationComponent
import com.chain.githubissues.di.DaggerViewModelComponent

class MainApplication : Application() {
    lateinit var component: ApplicationComponent

    companion object {
        private var INSTANCE: MainApplication? = null

        @JvmStatic
        fun get(): MainApplication = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        component = initDagger()
    }

    private fun initDagger(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .viewModelComponent(
                DaggerViewModelComponent
                    .builder()
                    .contextModule(ContextModule(this))
                    .build()
            )
            .build()
    }
}

class Injector private constructor() {
    companion object {
        fun get(): ApplicationComponent = MainApplication.get().component
    }
}