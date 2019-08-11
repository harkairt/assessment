package com.chain.githubissues.di

import com.chain.githubissues.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ViewModelComponent::class])
interface ApplicationComponent {
    fun injectInto(mainActivity: MainActivity)
}