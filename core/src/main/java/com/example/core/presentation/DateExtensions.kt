package com.example.core.presentation

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toInstantOrNull(): Instant? =
    runCatching { Instant.parse(this) }.getOrNull()

fun Instant.toUiDateString(
    pattern: String,
    locale: Locale = Locale.getDefault(),
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val formatter = DateTimeFormatter
        .ofPattern(pattern, locale)
        .withZone(zoneId)

    return formatter
        .format(this)
        .lowercase(locale)
}
