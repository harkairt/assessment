package com.chain.githubissues

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.helper.TestIssue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime

class IssueTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "1, opened 1 minute ago",
            "35, opened 35 minutes ago",
            "80, opened 1 hour ago",
            "240, opened 4 hours ago",
            "2820, opened 47 hours ago"
        ], delimiter = ','
    )
    fun `should return opened date in ago format`(
        minuteOffset: String,
        expectedDateInformation: String
    ) {
        val issue = TestIssue(created_at = LocalDateTime.now().minusMinutes(minuteOffset.toLong()))

        assert(issue.relevanteDateInformation == expectedDateInformation)
    }


    @ParameterizedTest
    @CsvSource(
        value = [
            "1, closed 1 minute ago",
            "35, closed 35 minutes ago",
            "80, closed 1 hour ago",
            "240, closed 4 hours ago",
            "2820, closed 47 hours ago"
        ], delimiter = ','
    )
    fun `should return closed date in ago format when closedAt is not null`(
        minuteOffset: String,
        expectedDateInformation: String
    ) {
        val issue = TestIssue(
            created_at = LocalDateTime.now(),
            closed_at = LocalDateTime.now().minusMinutes(minuteOffset.toLong())
        )

        assert(issue.relevanteDateInformation == expectedDateInformation)
    }


}