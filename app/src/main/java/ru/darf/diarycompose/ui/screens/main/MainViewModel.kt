package ru.darf.diarycompose.ui.screens.main

import androidx.compose.runtime.Immutable
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.ui.models.EventModelUi
import ru.darf.diarycompose.ui.screens.event.EventScreen
import javax.inject.Inject

@Immutable
data class MainViewState(
    val selectedDate: String = "",
    val events: List<EventModelUi> = emptyList(),
)

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()

    fun goToEventScreen(navController: NavHostController, eventId: String) {
        EventScreen.route(navController, eventId)
    }
}