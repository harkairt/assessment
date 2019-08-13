package com.chain.githubissues.di

import com.chain.githubissues.adapter.LocalDateTimeAdapter
import com.chain.githubissues.data.GithubWebservice
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ServiceModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(LocalDateTimeAdapter())
                        .build()
                )
            ).build()
    }

    @Provides
    fun webservice(): GithubWebservice {
        return retrofit.create(GithubWebservice::class.java)
    }

}