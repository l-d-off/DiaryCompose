package ru.darf.diarycompose.data.mapper

import ru.darf.diarycompose.data.database.EventModelDb
import ru.darf.diarycompose.data.network.model.EventDto
import ru.darf.diarycompose.domain.model.EventModel
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class EventMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: EventDto) = EventModelDb(
        id = dto.id,
        dateStart = dto.dateStart,
        dateFinish = dto.dateFinish,
        name = dto.name,
        description = dto.description
    )

    fun mapDbModelToEntity(db: EventModelDb) = EventModel(
        id = db.id ?: 0,
        name = db.name,
        dateStart = convertTimestampToDate(db.dateStart),
        dateFinish = convertTimestampToDate(db.dateFinish),
        description = db.description
    )

    fun mapEntityToDbModel(event: EventModel): EventModelDb {
        val dateStart = event.dateStart
        val dateFinish = event.dateFinish
        return EventModelDb(
            id = if (event.id == -1) null else event.id,
            dateStart = convertLocalDateTimeToTimestamp(dateStart),
            dateFinish = convertLocalDateTimeToTimestamp(dateFinish),
            name = event.name,
            description = event.description
        )
    }

    private fun convertLocalDateTimeToTimestamp(date: String): Long {
        val pattern = "dd.MM.yyyy HH:mm"
        val dateFormatter = DateTimeFormatter.ofPattern(pattern)
        val ldt = LocalDateTime.parse(date, dateFormatter)
        return ldt.toEpochSecond(ZoneOffset.of(OFFSET))
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val stamp = Timestamp(timestamp * KOEF)
        val date = Date(stamp.time)
        val pattern = "dd.MM.yyyy HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        private const val OFFSET = "+03:00"
        private const val KOEF = 1000
    }
}