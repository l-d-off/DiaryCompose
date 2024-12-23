package ru.darf.diarycompose.core.utils.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ru.darf.diarycompose.core.ext.checkLifecycleResumed

abstract class BaseViewModel : ViewModel() {

    fun popBackStack(navController: NavHostController) {
        navController.checkLifecycleResumed { navController.popBackStack() }
    }
}