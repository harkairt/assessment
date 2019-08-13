package com.chain.githubissues.domain.entity

enum class IssueState {
    open,
    closed;

    fun flip() : IssueState {
        if (this == open)
            return closed

        return open
    }

    companion object{
        fun from(charSequence: CharSequence): IssueState {
            return valueOf(charSequence.toString().toLowerCase())
        }
    }


}