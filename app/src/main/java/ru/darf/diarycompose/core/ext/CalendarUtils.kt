package ru.darf.diarycompose.core.ext

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CalendarUtils {
    companion object {
        var selectedDate: LocalDate = LocalDate.now()

        fun formattedShortTime(time: LocalTime): String? {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return time.format(formatter)
        }

        fun monthDayFromDate(date: LocalDate): String? {
            val formatter = DateTimeFormatter.ofPattern("MMMM d")
            return date.format(formatter)
        }

        fun formattedFullDate(date: LocalDate): String? {
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
            return date.format(formatter)
        }

        fun dateToMillis(date: String, time: LocalTime): Long {
            val l = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return when (time) {
                LocalTime.MIDNIGHT -> l.atTime(LocalTime.MIDNIGHT)
                    .toInstant(ZoneOffset.UTC).epochSecond

                else -> l.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC).epochSecond
            }
        }
    }
}