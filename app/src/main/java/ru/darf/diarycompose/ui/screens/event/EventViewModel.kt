package ru.darf.diarycompose.ui.screens.event

import androidx.compose.runtime.Immutable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.ui.models.EventModelUi

@Immutable
data class EventViewState(
    val event: EventModelUi? = null,
)

@HiltViewModel(assistedFactory = EventViewModel.Factory::class)
class EventViewModel @AssistedInject constructor(
    @Assisted eventId: String,
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(EventViewState())
    val viewState = _viewState.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(eventId: String): EventViewModel
    }
}