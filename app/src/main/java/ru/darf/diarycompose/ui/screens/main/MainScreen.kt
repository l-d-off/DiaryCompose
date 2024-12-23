package ru.darf.diarycompose.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import ru.darf.diarycompose.R
import ru.darf.diarycompose.core.ext.asPainter
import ru.darf.diarycompose.core.ext.asString
import ru.darf.diarycompose.core.ext.asVector
import ru.darf.diarycompose.core.ext.clickableNoRipple
import ru.darf.diarycompose.core.ext.hiltViewModelApp
import ru.darf.diarycompose.core.utils.router.ScreenCompanionRouter
import ru.darf.diarycompose.core.utils.ui.BaseScreen
import ru.darf.diarycompose.ui.core.AppTopBar
import ru.darf.diarycompose.ui.core.IconButton
import ru.darf.diarycompose.ui.theme.AppTheme
import ru.darf.diarycompose.ui.theme.backgroundPrimary
import ru.darf.diarycompose.ui.theme.backgroundSecondary
import ru.darf.diarycompose.ui.theme.backgroundTertiary
import ru.darf.diarycompose.ui.theme.sky
import ru.darf.diarycompose.ui.theme.textField
import ru.darf.diarycompose.ui.theme.textPrimary
import ru.darf.diarycompose.ui.theme.textSecondary
import ru.darf.diarycompose.ui.theme.topIconTint
import java.time.LocalDate
import java.time.YearMonth

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

        MainContent(viewModel, viewState, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    viewModel: MainViewModel,
    viewState: MainViewState,
    navController: NavHostController,
) {
    val hours = remember { (0..23).toList() }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: ""

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colors.backgroundPrimary,
        topBar = {
            AppTopBar(
                textTitle = R.string.app_name.asString(),
                rightContent = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = R.drawable.ic_trash.asPainter(),
                        contentDescription = "",
                        tint = AppTheme.colors.topIconTint
                    )
                }
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier.padding(8.dp),
                imageVector = R.drawable.ic_add.asVector(),
                contentPadding = PaddingValues(16.dp),
                hasRipple = true,
                borderColor = AppTheme.colors.textPrimary
            )
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
            hours.forEach { hour ->
                item {
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (hour < 10) "0$hour:00" else "$hour:00",
                            color = AppTheme.colors.textPrimary
                        )
                        HorizontalDivider(color = AppTheme.colors.textSecondary)
                        // TODO - if (startTime.hour == hour) drawEventCard()
                    }
                }
                item {
                    EventCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        title = "Title",
                        time = "10:00-12:00"
                    )
                }
            }
        }

//        AnimatedVisibility(
//            visible = currentMeeting != null,
//            enter = fadeIn(animationSpec = tween(300)) + scaleIn(
//                initialScale = 0.8f,
//                animationSpec = tween(300)
//            ),
//            exit = fadeOut(animationSpec = tween(300)) + scaleOut(
//                targetScale = 0.8f,
//                animationSpec = tween(300)
//            )
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(AppTheme.colors.backgroundPrimary.copy(0.5f))
//                    .clickableNoRipple(onClick = onMeetingClose)
//            ) {
//                currentMeeting?.let {
//                    MeetingShortInfo(
//                        modifier = Modifier
//                            .padding(dimensionResource(R.dimen.default_padding))
//                            .align(Alignment.BottomCenter)
//                            .clickable(null, null) {},
//                        meeting = Meeting(
//                            id = currentMeeting.id,
//                            name = currentMeeting.name,
//                            description = currentMeeting.description,
//                            senseTags = currentMeeting.senseTags,
//                            author = Author("", "", "", "", ""),
//                            startDate = currentMeeting.startDate.transformToRu(),
//                            maxParticipants = 0,
//                            currentParticipants = 0,
//                            contactParticipants = 0,
//                            latitude = currentMeeting.latitude,
//                            longitude = currentMeeting.longitude,
//                            isValidated = false,
//                            isContribution = false,
//                            coverId = currentMeeting.coverUrl.split('/').last(),
//                            promoUrl = ""
//                        ),
//                        token = token
//                    )
//                }
//            }
//        }
    }
}

@Composable
private fun EventCard(modifier: Modifier = Modifier, title: String, time: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(AppTheme.colors.sky)
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = AppTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = time,
            color = AppTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EventCardDetails() {

}