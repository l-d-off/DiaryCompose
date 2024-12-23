package ru.darf.diarycompose.core.utils.router

import androidx.navigation.NavHostController
import ru.darf.diarycompose.core.ext.addArgs
import ru.darf.diarycompose.core.ext.getQualifiedName

/** [keyName] принимает имя класса, в котором компаньон наследует ScreenCompanionRouter */
abstract class ScreenCompanionRouter {
    val keyName = "${this::class.getQualifiedName}/"
    open val route = keyName.addArgs()
    open fun route(host: NavHostController?): Unit? = null
}