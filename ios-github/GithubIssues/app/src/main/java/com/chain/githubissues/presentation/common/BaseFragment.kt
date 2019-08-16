package com.chain.githubissues.presentation.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.chain.githubissues.di.ViewModelFactory
import com.chain.githubissues.domain.entity.Issue
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {
    protected fun <T : ViewModel> ViewModelFactory.getFragmentScopedViewModel(viewModelKClass: KClass<T>): T {
        return ViewModelProviders.of(this@BaseFragment, this).get(viewModelKClass.java)
    }
}