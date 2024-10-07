import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@Composable
@Preview
fun App() {
    val model by LocalMainViewModel.singleton()
    val scope by LocalCpuDispatcherScope.singleton()
    val serviceProvider by LocalServiceProvider.singleton()

    val text by remember { model.text }
    val counter by remember { model.counter }
    val timerFlow = remember { model.timerFlow }
    val lifecycle = remember { model.lifecycleFlow }.collectAsState(initial = Lifecycle.Initializing)
    val lifecycleText = mutableStateOf(Lifecycle.Initializing::class.java.simpleName)

    val someService = ServiceProvider.getService(ServiceKeys.SomeServiceKey)
    val exampleService = ServiceProvider.getService(ServiceKeys.ExampleServiceKey)

    val disposable = remember { Disposable("main") }

    someService?.start()

    Theme {

        Column {

            TopBar(
                title = "Compose for Desktop",
                modifier = Modifier.fillMaxWidth(),
                onClose = { model.stopApplication() },
                onMinimize = { model.windowState.isMinimized = true },
                onMaximize = {
                    val windowState = model.windowState
                    windowState.isMinimized = false
                    if (windowState.placement != WindowPlacement.Maximized) {
                        windowState.placement = WindowPlacement.Maximized
                    } else {
                        windowState.placement = WindowPlacement.Floating
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center,
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Button(
                        modifier = Modifier.background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(50f)
                        ),
                        onClick = {
                            model.updateText("Hello, Desktop!")
                            disposable.dispose()
                        },
                        shape = RoundedCornerShape(50f)
                    ) {
                        Text(text)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier.background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(50f)
                        ),
                        onClick = {
                            model.stopApplication()
                        },
                        shape = RoundedCornerShape(50f)
                    ) {
                        Text("Exit $counter")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = text,
                        color = MaterialTheme.colors.onBackground,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = lifecycleText.value,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }

        LaunchedEffect(key1 = "lifecycle", key2 = lifecycle.value) {

            lifecycleText.value = "lifecycle: ${lifecycle.value::class.java.simpleName}"
        }

        DisposableEffect(key1 = "timerFlow", key2 = timerFlow) {

            val timerFlowDisposable = Disposable("timerFlow") {

                println("timeFlowDisposable.dispose()")
            }

            timerFlow
                .onStart {

                    model.lifecycleFlow.emit(Lifecycle.Started)
                }
                .onCompletion {

                    model.lifecycleFlow.emit(Lifecycle.Stopped)
                }
                .onEach {

                    println("onEach: ${Thread.currentThread().name} $it")
                    model.incrementCounter()
                }
                .takeUntil { disposable.isDisposed() }
                .launchIn(scope)

            onDispose { timerFlowDisposable.dispose() }
        }

        DisposableEffect(key1 = "someService", key2 = someService?.channel) {

            val someServiceDisposable = Disposable("someService") {
                println("someServiceDisposable.dispose()")
                someService?.stop()
            }
            someService?.channel?.run {

                onEach {

                    println(it)
                }
                    .takeUntil { someServiceDisposable.isDisposed() }
                    .launchIn(scope)
            }

            onDispose { someServiceDisposable.dispose() }
        }

        DisposableEffect("main") {

            onDispose {

                println("mainDisposable onDispose")
                disposable.dispose()
            }
        }
    }
}
