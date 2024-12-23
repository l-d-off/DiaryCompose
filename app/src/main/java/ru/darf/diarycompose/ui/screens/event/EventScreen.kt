package ru.darf.diarycompose.ui.screens.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.addArgs
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.core.ext.hiltViewModelApp
import ru.darf.diarycompose.core.utils.router.ScreenCompanionRouter
import ru.darf.diarycompose.core.utils.ui.BaseScreen
import ru.darf.diarycompose.ui.core.AppTopBar
import ru.darf.diarycompose.ui.theme.AppTheme

class EventScreen(
    navBuilder: NavGraphBuilder,
    navController: NavHostController,
) : BaseScreen(navBuilder, navController) {

    companion object : ScreenCompanionRouter() {
        private const val EVENT_ID_KEY = "eventId"
        override val route = keyName.addArgs(EVENT_ID_KEY)
        fun route(host: NavHostController?, eventId: String) {
            val routeArg = keyName.addArgs(EVENT_ID_KEY to eventId)
            host?.navigate(routeArg)
        }
    }

    fun addScreen() = addScreenWithAnimation(route) { entry ->
        val viewModel = hiltViewModelApp<EventViewModel>()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        EventContent(viewModel, viewState, navController)
    }
}

@Composable
private fun EventContent(
    viewModel: EventViewModel,
    viewState: EventViewState,
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.backgroundPrimary,
        topBar = {
            AppTopBar(textTitle = R.string.app_name.asString())
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp + it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // TODO - календарь
            item {

            }
//            items()
        }
    }
}