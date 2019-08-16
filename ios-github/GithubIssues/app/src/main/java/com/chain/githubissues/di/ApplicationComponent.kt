package com.chain.githubissues.di

import com.chain.githubissues.MainActivity
import com.chain.githubissues.presentation.issueDetail.IssueDetailFragment
import com.chain.githubissues.presentation.issueList.IssueListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ViewModelComponent::class])
interface ApplicationComponent {
    fun injectInto(mainActivity: MainActivity)
    fun injectInto(issueListFragment: IssueListFragment)
    fun injectInto(issueDetailFragment: IssueDetailFragment)
}