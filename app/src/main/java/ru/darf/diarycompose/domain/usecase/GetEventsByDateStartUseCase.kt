package ru.darf.diarycompose.domain.usecase

import ru.darf.diarycompose.domain.EventRepository
import javax.inject.Inject

class GetEventsByDateStartUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(dateStart: Long, dateFinish: Long) =
        repository.getEventsByDateStart(dateStart, dateFinish)
}