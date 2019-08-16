package com.chain.githubissues.data

import com.chain.githubissues.domain.entity.Issue
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val pageSize = 50

interface GithubWebservice {

    @GET("repos/{author}/{repo}/issues")
    fun getIssues(
        @Path("author") author: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = pageSize
    ): Single<List<Issue>>

    @GET("repos/{author}/{repo}/issues/{issueNumber}")
    fun getIssue(
        @Path("author") author: String,
        @Path("repo") repo: String,
        @Path("issueNumber") issueNumber: Int
    ): Single<Issue>

}