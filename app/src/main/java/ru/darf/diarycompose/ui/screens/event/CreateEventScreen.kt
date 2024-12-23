package ru.darf.diarycompose.ui.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.core.ext.asVector
import ru.darf.diarycompose.core.ext.hiltViewModelApp
import ru.darf.diarycompose.core.utils.router.ScreenCompanionRouter
import ru.darf.diarycompose.core.utils.ui.BaseScreen
import ru.darf.diarycompose.ui.core.AppTopBar
import ru.darf.diarycompose.ui.core.IconButton
import ru.darf.diarycompose.ui.core.InputTextField
import ru.darf.diarycompose.ui.models.EventModelUi
import ru.darf.diarycompose.ui.theme.AppTheme

class CreateEventScreen(
    navBuilder: NavGraphBuilder,
    navController: NavHostController,
) : BaseScreen(navBuilder, navController) {

    companion object : ScreenCompanionRouter() {
        override fun route(host: NavHostController?) = host?.navigate(route)
    }

    fun addScreen() = addScreenWithAnimation(route) { entry ->
        val viewModel = hiltViewModelApp<CreateEventViewModel>()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()
        val event = viewState.event

        EventContent(
            viewModel = viewModel,
            viewState = viewState,
            onBackClick = { viewModel.popBackStack(navController) }
        )
    }
}

@Composable
private fun EventContent(
    viewModel: CreateEventViewModel,
    viewState: CreateEventViewState,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.backgroundPrimary,
        topBar = {
            AppTopBar(
                textTitle = R.string.creating_event.asString(),
                leftContent = {
                    IconButton(
                        modifier = Modifier.rotate(180f),
                        imageVector = R.drawable.ic_arrow.asVector(),
                        onClick = onBackClick,
                        contentPadding = PaddingValues(8.dp),
                        hasRipple = true,
                        backgroundColor = AppTheme.colors.backgroundSecondary
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = it.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputTextField(value = TextFieldValue(""), onValueChange = {}, onFocusedChange = {}, placeholder = "Value")
            InputTextField(value = TextFieldValue(""), onValueChange = {}, onFocusedChange = {}, placeholder = "Value")
        }
    }
}