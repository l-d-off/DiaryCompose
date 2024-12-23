package ru.darf.diarycompose.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.darf.diarycompose.ui.screens.event.CreateEventScreen
import ru.darf.diarycompose.ui.screens.main.MainScreen
import ru.darf.diarycompose.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val snackBarHost = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
            .systemBarsPadding()
            .fillMaxSize()
            .imePadding(),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(16.dp),
                hostState = snackBarHost
            ) { snackbarData ->
//                CustomSnackBar(message = snackbarData.visuals.message)
            }
        }
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = MainScreen.route
        ) {
            MainScreen(this, navController).addScreen()
            CreateEventScreen(this, navController).addScreen()
        }
    }
}
