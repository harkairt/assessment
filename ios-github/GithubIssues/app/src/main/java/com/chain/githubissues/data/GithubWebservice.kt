package com.chain.githubissues.data

import com.chain.githubissues.domain.entity.Issue
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubWebservice {

    @GET("repos/{author}/{repo}/issues")
    fun getIssues(@Path("author") author: String,
                  @Path("repo") repo: String,
                  @Query("state") state: String = "all",
                  @Query("per_page") perPage: Int = 100)
            : Single<List<Issue>>


}