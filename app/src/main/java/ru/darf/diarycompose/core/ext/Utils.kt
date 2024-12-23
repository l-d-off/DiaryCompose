package ru.darf.diarycompose.core.ext

import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import ru.darf.diarycompose.core.utils.viewmodel.BaseViewModel
import ru.darf.diarycompose.ui.screens.event.EventViewModel
import java.util.UUID
import kotlin.jvm.internal.ClassBasedDeclarationContainer
import kotlin.reflect.KClass

@Suppress("UPPER_BOUND_VIOLATED")
val <T> KClass<T>.getQualifiedName: String
    @JvmName("getJavaClass")
    get() = ((this as ClassBasedDeclarationContainer).jClass)
        .enclosingClass
        ?.kotlin
        ?.qualifiedName
        ?: UUID.randomUUID().toString()

@Composable
inline fun <T> rememberState(crossinline producer: @DisallowComposableCalls () -> T) =
    remember { mutableStateOf(producer()) }

@Composable
inline fun <reified VM : BaseViewModel> hiltViewModelApp(
    owner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
) = hiltViewModel<VM>(owner)

@Composable
fun hiltViewModelEvent(
    eventId: String,
    owner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
) = hiltViewModel<EventViewModel, EventViewModel.Factory>(owner) { factory ->
    factory.create(eventId)
}

@Composable
fun @receiver:StringRes Int.asString() = stringResource(this)

@Composable
fun @receiver:StringRes Int.asString(vararg formatArgs: Any) = stringResource(this, *formatArgs)

@Composable
fun @receiver:PluralsRes Int.asPlural(count: Int) = pluralStringResource(this, count, count)

@Composable
fun @receiver:DrawableRes Int.asPainter() = painterResource(this)

@Composable
fun @receiver:DrawableRes Int.asVector() = ImageVector.vectorResource(this)

@Composable
fun rememberOpenIntentUrl(onError: (String) -> Unit = {}): (String?, String) -> Boolean {
    val uriHandler = LocalUriHandler.current
    return { intentName, uri ->
        try {
            uriHandler.openUri(intentName.orEmpty() + uri)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            onError.invoke(uri)
            false
        }
    }
}

/**
 * Проверка жизненного цикла экрана в navController, чтобы корректно сделать, например, popBackStack
 */
fun NavHostController.checkLifecycleResumed(action: () -> Unit) {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        action()
    }
}