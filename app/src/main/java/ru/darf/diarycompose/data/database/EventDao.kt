package ru.darf.diarycompose.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.darf.diarycompose.domain.model.EventModel

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE dateStart BETWEEN :dateStart AND :dateFinish")
    suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long): List<EventModelDb>

    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventModelDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventModelDb)

    @Delete
    suspend fun deleteEvent(event: EventModelDb)
}