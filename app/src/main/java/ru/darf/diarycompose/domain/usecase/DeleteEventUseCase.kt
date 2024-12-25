package ru.darf.diarycompose.domain.usecase

import ru.darf.diarycompose.domain.EventRepository
import ru.darf.diarycompose.domain.model.EventModel
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(private val repository: EventRepository) {
    suspend operator fun invoke(event: EventModel) = repository.deleteEvent(event)
}