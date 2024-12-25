package ru.darf.diarycompose.domain.usecase

import ru.darf.diarycompose.domain.EventRepository
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val repository: EventRepository,
) {
    suspend operator fun invoke() = repository.getEvents()
}