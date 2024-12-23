package ru.darf.diarycompose.ui.screens.event

import androidx.compose.runtime.Immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.ui.models.EventModelUi
import javax.inject.Inject

@Immutable
data class CreateEventViewState(
    val event: EventModelUi? = null,
)

@HiltViewModel
class CreateEventViewModel @Inject constructor() : BaseViewModel() {

    private val _viewState = MutableStateFlow(CreateEventViewState())
    val viewState = _viewState.asStateFlow()

    // TODO
    fun saveEvent() {

    }
}