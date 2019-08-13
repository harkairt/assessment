package com.chain.githubissues

import com.chain.githubissues.domain.entity.IssueState
import org.junit.Test

class EnumTest {

    @Test
    fun `enum should equal to its name`() {
        val issueState = IssueState.open

        assert(issueState.toString() == "open")
    }

}