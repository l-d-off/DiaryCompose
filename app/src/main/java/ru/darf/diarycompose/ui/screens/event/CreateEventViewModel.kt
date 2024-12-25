package ru.darf.diarycompose.ui.screens.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.domain.model.EventModel
import ru.darf.diarycompose.domain.usecase.SaveNewEventUseCase

@Immutable
data class CreateEventViewState(
    val time: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
)

@HiltViewModel(assistedFactory = CreateEventViewModel.Factory::class)
class CreateEventViewModel @AssistedInject constructor(
    private val saveNewEventUseCase: SaveNewEventUseCase,
    @Assisted private val date: String,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreateEventViewState())
    val viewState = _viewState.asStateFlow()

    fun saveNewEvent(
        navController: NavHostController,
        sendMessage: (String) -> Unit,
    ) {
        val state = viewState.value
        if (state.time.text.length < 4) {
            sendMessage("Время должно быть указано полностью")
            return
        }
        if (state.name.text.isEmpty()) {
            sendMessage("Название не должно быть пустым")
            return
        }
        viewModelScope.launch {
            saveNewEventUseCase(
                EventModel(
                    dateStart = "$date ${state.time.text.take(2)}:00",
                    dateFinish = "$date ${state.time.text.takeLast(2)}:00",
                    name = state.name.text,
                    description = state.description.text
                )
            )
            sendMessage("Запись создана")
            popBackStack(navController)
        }
    }

    fun updateTime(newTime: TextFieldValue) {
        _viewState.update { it.copy(time = newTime) }
    }

    fun updateName(newName: TextFieldValue) {
        _viewState.update { it.copy(name = newName) }
    }

    fun updateDescription(newDescription: TextFieldValue) {
        _viewState.update { it.copy(description = newDescription) }
    }

    @AssistedFactory
    interface Factory {
        fun create(date: String): CreateEventViewModel
    }
}