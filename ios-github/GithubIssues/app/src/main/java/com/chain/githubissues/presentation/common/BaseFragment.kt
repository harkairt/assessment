package com.chain.githubissues.presentation.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.chain.githubissues.di.ViewModelFactory
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {
    protected fun <T : ViewModel> ViewModelFactory.getFragmentScopedViewModel(viewModelKClass: KClass<T>): T {
        return androidx.lifecycle.ViewModelProviders.of(this@BaseFragment, this).get(viewModelKClass.java)
    }
}