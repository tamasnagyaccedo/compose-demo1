import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

val LocalMainViewModel =
    staticCompositionLocalOf<Singleton<MainViewModel>> { throw IllegalStateException("LocalMainViewModel not present") }

val LocalCpuDispatcherScope =
    staticCompositionLocalOf<Singleton<CoroutineScope>> { throw IllegalStateException("LocalDefaultDispatcherScope not present") }

val LocalServiceProvider =
    staticCompositionLocalOf<Singleton<ServiceProvider>> { throw IllegalStateException("LocalServiceProvider not present") }

val LocalExampleService =
    staticCompositionLocalOf<Prototype<SomeService>> { throw IllegalStateException("LocalExampleService not present") }

fun main() = runBlocking {

    awaitApplication("main") {

        val windowState = rememberWindowState(width = 400.dp, height = 400.dp)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            undecorated = true,
        ) {

            module {

                val viewModel = MainViewModel(this@awaitApplication, windowState)

                declare { LocalMainViewModel provides singleton { viewModel } }
                declare { LocalCpuDispatcherScope provides singleton { CoroutineScope(Dispatchers.Default) } }
                declare { LocalServiceProvider provides singleton { ServiceProvider } }
                declare { LocalExampleService provides prototype { SomeService() } }

                content {

                    App()
                }
            }
        }
    }
}
