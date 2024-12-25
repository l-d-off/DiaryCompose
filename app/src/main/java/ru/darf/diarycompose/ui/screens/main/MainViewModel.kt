package ru.darf.diarycompose.ui.screens.main

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.domain.model.EventModel
import ru.darf.diarycompose.domain.usecase.DeleteEventUseCase
import ru.darf.diarycompose.domain.usecase.GetEventsUseCase
import ru.darf.diarycompose.domain.usecase.LoadDataUseCase
import ru.darf.diarycompose.ui.screens.event.CreateEventScreen
import javax.inject.Inject

@Immutable
data class MainViewState(
    val selectedDate: String = "",
    val events: List<EventModel> = emptyList(),
    val currentEvent: EventModel? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    loadDataUseCase: LoadDataUseCase,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()

    private var job: Job? = null

    init {
        loadDataUseCase()
    }

    fun goToCreateEventScreen(navController: NavHostController) {
        val state = viewState.value
        CreateEventScreen.route(navController, state.selectedDate)
    }

    fun openEventDetails(eventId: Int) {
        _viewState.update {
            val event = it.events.find { event -> event.id == eventId }
            if (event != null) {
                it.copy(currentEvent = event)
            } else {
                it
            }
        }
    }

    fun closeEventDetails() {
        _viewState.update { it.copy(currentEvent = null) }
    }

    fun updateSelectedDate(newSelectedDate: String) {
        _viewState.update { it.copy(selectedDate = newSelectedDate) }
    }

    fun getEvents() {
        job?.cancel()
        job = viewModelScope.launch {
            _viewState.update {
                it.copy(
                    events = getEventsUseCase()
                )
            }
        }
    }

    fun deleteEvent(sendMessage: (String) -> Unit) {
        val state = viewState.value
        if (state.currentEvent != null) {
            viewModelScope.launch {
                deleteEventUseCase(state.currentEvent)
                _viewState.update {
                    it.copy(
                        currentEvent = null,
                        events = getEventsUseCase()
                    )
                }
                sendMessage("Запись удалена")
            }
        }
    }
}