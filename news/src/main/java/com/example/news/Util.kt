package com.example.news

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toInstantOrNull(): Instant? =
    runCatching { Instant.parse(this) }.getOrNull()

private val articleDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern(
        "d MMM yyyy 'at' h:mm a",
        Locale.getDefault()
    )

fun Instant.toUiDateString(): String =
    articleDateFormatter
        .withZone(ZoneId.systemDefault())
        .format(this)
        .lowercase()