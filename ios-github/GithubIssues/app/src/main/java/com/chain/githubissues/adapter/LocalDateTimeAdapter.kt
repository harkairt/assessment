package com.chain.githubissues.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime

class LocalDateTimeAdapter {
    @FromJson
    fun fromJson(dateString: String): LocalDateTime {
        val regularDateString = dateString.substring(0, dateString.length - 1)

        return LocalDateTime.parse(regularDateString)
    }

    @ToJson
    fun toJson(localDateTime: LocalDateTime): String {
        return localDateTime.toString()
    }
}