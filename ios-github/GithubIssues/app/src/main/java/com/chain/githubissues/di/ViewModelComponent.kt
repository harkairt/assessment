package com.chain.githubissues.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chain.githubissues.presentation.issueDetail.IssueDetailViewModel
import com.chain.githubissues.presentation.issueList.IssueListViewModel
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
    private val viewModelProviders: Map<
            Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Component(
    modules = [
        ViewModelModule::class,
        RepositoryBindingModule::class,
        ServiceModule::class,
        ContextModule::class
    ]
)
interface ViewModelComponent {
    fun viewModelProviders(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
}

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(IssueListViewModel::class)
    fun bindIssueListViewModel(viewModel: IssueListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IssueDetailViewModel::class)
    fun bindIssueDetailViewModel(viewModel: IssueDetailViewModel): ViewModel
}