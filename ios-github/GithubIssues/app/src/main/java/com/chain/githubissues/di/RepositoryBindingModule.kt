package com.chain.githubissues.di

import com.chain.githubissues.data.IssueRepositoryImpl
import com.chain.githubissues.domain.repository.IssueRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryBindingModule {

    @Binds
    fun bindRepository(issueRepository: IssueRepositoryImpl): IssueRepository

}