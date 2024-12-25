package ru.darf.diarycompose.domain

import ru.darf.diarycompose.domain.model.EventModel

interface EventRepository {

    suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long): List<EventModel>

    suspend fun getEvents(): List<EventModel>

    fun loadData()

    suspend fun saveNewEvent(event: EventModel)

    suspend fun deleteEvent(event: EventModel)
}