package ru.darf.diarycompose.data.repository

import android.app.Application
import ru.darf.diarycompose.data.database.EventDao
import ru.darf.diarycompose.data.mapper.EventMapper
import ru.darf.diarycompose.data.network.ApiService
import ru.darf.diarycompose.domain.EventRepository
import ru.darf.diarycompose.domain.model.EventModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    application: Application,
    private val mapper: EventMapper,
    private val eventDao: EventDao,
    private val apiService: ApiService,
) : EventRepository {

    private val event = apiService.getEvent(application)

    override suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long) =
        eventDao.getEventsByDateStart(dateStart, dateFinish).map {
            mapper.mapDbModelToEntity(it)
        }

    override suspend fun getEvents() =
        eventDao.getEvents().map {
            mapper.mapDbModelToEntity(it)
        }

    override fun loadData() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            eventDao.insertEvent(mapper.mapDtoToDbModel(event))
        }
    }

    override suspend fun saveNewEvent(event: EventModel) {
        eventDao.insertEvent(mapper.mapEntityToDbModel(event))
    }

    override suspend fun deleteEvent(event: EventModel) {
        eventDao.deleteEvent(mapper.mapEntityToDbModel(event))
    }
}