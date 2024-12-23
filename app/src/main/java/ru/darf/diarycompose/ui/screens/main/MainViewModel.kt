package ru.darf.diarycompose.ui.screens.main

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.ui.models.EventModelUi
import ru.darf.diarycompose.ui.screens.event.EventScreen
import javax.inject.Inject

@Immutable
data class MainViewState(
    val selectedDate: String = "",
    val events: List<EventModelUi> = emptyList(),
    val currentEvent: EventModelUi? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()

    fun goToEventScreen(navController: NavHostController, eventId: String) {
        EventScreen.route(navController, eventId)
    }

    // TODO
    fun openEventDetails(eventId: String = "id0010101") {
        _viewState.update {
            it.copy(
                currentEvent = EventModelUi(
                    id = eventId,
                    dateStart = 0,
                    dateFinish = 0,
                    name = "Заметка",
                    description = "Описание заметки и ещё какой-то текст, чтобы заполнить пространство"
                )
            )
        }
//        viewModelScope.launch {
//            meetingUseCase.getMeetingDetails(
//                meetingId = meetingId,
//                startFlow = ::gDLoaderStart,
//                messageFlow = ::message,
//                errorFlow = ::gDLoaderStop,
//                successFlow = { meeting ->
//                    _viewState.update {
//                        it.copy(currentMeeting = meeting)
//                    }
//                }
//            )
//        }
    }

    fun closeEventDetails() {
        _viewState.update { it.copy(currentEvent = null) }
    }
}