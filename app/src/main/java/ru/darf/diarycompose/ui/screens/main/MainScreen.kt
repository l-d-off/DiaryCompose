package ru.darf.diarycompose.ui.screens.main

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.core.ext.asVector
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.core.ext.clickableRipple
import ru.darf.diarycompose.core.ext.hiltViewModelApp
import ru.darf.diarycompose.core.ext.toDateString
import ru.darf.diarycompose.core.ext.unixToDateString
import ru.darf.diarycompose.core.utils.router.ScreenCompanionRouter
import ru.darf.diarycompose.core.utils.ui.BaseScreen
import ru.darf.diarycompose.domain.model.EventModel
import ru.darf.diarycompose.ui.core.AppTopBar
import ru.darf.diarycompose.ui.core.IconButton
import ru.darf.diarycompose.ui.theme.AppTheme
import ru.darf.diarycompose.ui.theme.topIconBackground
import ru.darf.diarycompose.ui.theme.topIconTint
import java.time.LocalDate

class MainScreen(
    navBuilder: NavGraphBuilder,
    navController: NavHostController,
) : BaseScreen(navBuilder, navController) {

    companion object : ScreenCompanionRouter() {
        override fun route(host: NavHostController?) = host?.navigate(route) {
            popUpTo(host.graph.findStartDestination().id) { inclusive = true }
        }
    }

    fun addScreen() = addScreenWithAnimation(route) { entry ->
        val viewModel = hiltViewModelApp<MainViewModel>()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        MainContent(
            viewModel = viewModel,
            viewState = viewState,
            navController = navController,
            currentEvent = viewState.currentEvent,
            onEventClose = viewModel::closeEventDetails
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    viewModel: MainViewModel,
    viewState: MainViewState,
    navController: NavHostController,
    currentEvent: EventModel?,
    onEventClose: () -> Unit,
) {
    val timelines = remember { (0 until 24).map { if (it < 10) "0$it:00" else "$it:00" } }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.unixToDateString()
    val context = LocalContext.current

    LaunchedEffect(selectedDate) {
        if (selectedDate != null) viewModel.updateSelectedDate(selectedDate)
        else viewModel.updateSelectedDate(LocalDate.now().toDateString())
        viewModel.getEvents()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.backgroundPrimary,
        topBar = {
            AppTopBar(
                textTitle = R.string.app_name.asString(),
//                rightContent = {
//                    Icon(
//                        modifier = Modifier.size(24.dp),
//                        painter = R.drawable.ic_trash.asPainter(),
//                        contentDescription = "",
//                        tint = AppTheme.colors.topIconTint
//                    )
//                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentEvent == null,
                enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(300)
                ),
                exit = fadeOut(animationSpec = tween(300)) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(300)
                )
            ) {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    imageVector = R.drawable.ic_add.asVector(),
                    contentPadding = PaddingValues(16.dp),
                    hasRipple = true,
                    borderColor = AppTheme.colors.textPrimary,
                    onClick = {
                        viewModel.goToCreateEventScreen(navController)
                    }
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = AppTheme.colors.backgroundPrimary,
                        titleContentColor = AppTheme.colors.textPrimary,
                        headlineContentColor = AppTheme.colors.textPrimary,
                        weekdayContentColor = AppTheme.colors.textSecondary,
                        subheadContentColor = AppTheme.colors.textSecondary,
                        navigationContentColor = AppTheme.colors.topIconTint,
                        yearContentColor = AppTheme.colors.textSecondary,
                        disabledYearContentColor = AppTheme.colors.textField,
                        currentYearContentColor = AppTheme.colors.sky,
                        selectedYearContentColor = AppTheme.colors.textPrimary,
                        disabledSelectedYearContentColor = AppTheme.colors.textSecondary.copy(alpha = 0.5f),
                        selectedYearContainerColor = AppTheme.colors.sky,
                        disabledSelectedYearContainerColor = AppTheme.colors.textField,
                        dayContentColor = AppTheme.colors.textPrimary,
                        disabledDayContentColor = AppTheme.colors.textSecondary.copy(alpha = 0.5f),
                        selectedDayContentColor = AppTheme.colors.textPrimary,
                        disabledSelectedDayContentColor = AppTheme.colors.textSecondary.copy(alpha = 0.5f),
                        selectedDayContainerColor = AppTheme.colors.sky,
                        disabledSelectedDayContainerColor = AppTheme.colors.textField,
                        todayContentColor = AppTheme.colors.sky,
                        todayDateBorderColor = AppTheme.colors.sky.copy(alpha = 0.5f),
                        dayInSelectionRangeContentColor = AppTheme.colors.textPrimary,
                        dayInSelectionRangeContainerColor = AppTheme.colors.sky.copy(alpha = 0.2f),
                        dividerColor = AppTheme.colors.backgroundSecondary
                    ),
                    title = null,
                    headline = null,
                    showModeToggle = false
                )
            }
            timelines.forEach { timeline ->
                item {
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = timeline,
                            color = AppTheme.colors.textPrimary
                        )
                        HorizontalDivider(color = AppTheme.colors.textSecondary)
                    }
                }
                viewState.events.forEach { event ->
                    if (timeline == event.dateStart.split(" ").last()) {
                        item(key = event.id) {
                            EventCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .animateItem(),
                                event = event,
                                onCardClick = { eventId -> viewModel.openEventDetails(eventId) }
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = currentEvent != null,
            enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(300)
            ),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.backgroundPrimary.copy(0.5f))
                    .clickableNoRipple(onClick = onEventClose)
            ) {
                currentEvent?.let { event ->
                    EventCardDetails(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                            .fillMaxHeight(0.5f)
                            .clickable(null, null) {},
                        event = event,
                        onDelete = {
                            viewModel.deleteEvent { message ->
                                Toast.makeText(
                                    context,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EventCard(
    modifier: Modifier = Modifier,
    event: EventModel,
    onCardClick: (eventId: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(AppTheme.colors.sky, RoundedCornerShape(4.dp))
            .clickableRipple(onClick = { onCardClick(event.id) })
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = event.name,
            color = AppTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${event.dateStart.split(" ").last()}-${event.dateFinish.split(" ").last()}",
            color = AppTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EventCardDetails(
    modifier: Modifier = Modifier,
    event: EventModel,
    onDelete: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AppTheme.colors.sky)
            .border(1.dp, AppTheme.colors.textPrimary, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = event.name,
                    color = AppTheme.colors.textPrimary,
                    fontSize = 20.sp
                )
                Text(
                    text = "${event.dateStart.split(" ").last()}-${
                        event.dateFinish.split(" ").last()
                    }",
                    color = AppTheme.colors.textPrimary,
                    fontSize = 16.sp
                )
            }
            IconButton(
                imageVector = R.drawable.ic_trash.asVector(),
                contentPadding = PaddingValues(8.dp),
                backgroundColor = topIconBackground,
                tint = topIconTint,
                hasRipple = true,
                onClick = onDelete
            )
        }
        HorizontalDivider(color = AppTheme.colors.textSecondary)
        Text(
            text = event.description,
            color = AppTheme.colors.textPrimary
        )
    }
}