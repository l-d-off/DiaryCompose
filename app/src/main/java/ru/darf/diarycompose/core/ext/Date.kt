package ru.darf.diarycompose.core.ext

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val LocaleRu = Locale("ru", "RU")

fun String.isDigits() = Regex("\\d*").matches(this)

/**
 * Формат:
 *
 * 01.01.2001 09:00
 *
 * 0L - не удалось преобразовать дату
 */
fun String.dateTimeToUnix(): Long = SimpleDateFormat("dd.MM.yyyy HH:mm", LocaleRu)
    .parse(this)?.time ?: 0

fun Long.unixToDateTimeString(): String = SimpleDateFormat("dd.MM.yyyy HH:mm", LocaleRu)
    .format(this)

fun Long.unixToDateString(): String = SimpleDateFormat("dd.MM.yyyy", LocaleRu)
    .format(this)

fun LocalDate.toDateString(): String = DateTimeFormatter
    .ofPattern("dd.MM.yyyy")
    .let { this.format(it) }