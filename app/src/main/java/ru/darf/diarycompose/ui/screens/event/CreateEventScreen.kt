package ru.darf.diarycompose.ui.screens.event

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.addArgs
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.core.ext.asVector
import ru.darf.diarycompose.core.ext.hiltViewModelApp
import ru.darf.diarycompose.core.utils.router.ScreenCompanionRouter
import ru.darf.diarycompose.core.utils.ui.BaseScreen
import ru.darf.diarycompose.ui.core.AppButton
import ru.darf.diarycompose.ui.core.AppTopBar
import ru.darf.diarycompose.ui.core.IconButton
import ru.darf.diarycompose.ui.core.InputTextField
import ru.darf.diarycompose.ui.core.TimeRangeTextField
import ru.darf.diarycompose.ui.theme.AppTheme

class CreateEventScreen(
    navBuilder: NavGraphBuilder,
    navController: NavHostController,
) : BaseScreen(navBuilder, navController) {

    companion object : ScreenCompanionRouter() {
        private const val DATE_KEY = "date"
        override val route = keyName.addArgs(DATE_KEY)
        fun route(host: NavHostController?, date: String) {
            val routeArg = keyName.addArgs(DATE_KEY to date)
            host?.navigate(routeArg)
        }
    }

    fun addScreen() = addScreenWithAnimation(route) { entry ->
        val date = entry.arguments?.getString(DATE_KEY) ?: ""
        val viewModel = hiltViewModelApp(date)
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        EventContent(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController
        )
    }
}

@Composable
private fun EventContent(
    viewModel: CreateEventViewModel,
    viewState: CreateEventViewState,
    navController: NavHostController,
) {
    val context = LocalContext.current
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
                        onClick = { viewModel.popBackStack(navController) },
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
            TimeRangeTextField(
                value = viewState.time,
                onValueChange = viewModel::updateTime,
                onFocusedChange = {},
                placeholder = R.string.placeholder_time.asString()
            )
            InputTextField(
                value = viewState.name,
                onValueChange = viewModel::updateName,
                onFocusedChange = {},
                placeholder = R.string.placeholder_name.asString()
            )
            InputTextField(
                modifier = Modifier.weight(1f),
                value = viewState.description,
                singleLine = false,
                onValueChange = viewModel::updateDescription,
                onFocusedChange = {},
                placeholder = R.string.placeholder_description.asString()
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.add_event.asString(),
                onClick = {
                    viewModel.saveNewEvent(navController) { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                hasRipple = true
            )
        }
    }
}