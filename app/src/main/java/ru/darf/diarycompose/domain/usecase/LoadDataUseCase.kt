package ru.darf.diarycompose.domain.usecase

import ru.darf.diarycompose.domain.EventRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke() = repository.loadData()
}