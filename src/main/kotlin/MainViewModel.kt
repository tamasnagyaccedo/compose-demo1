import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowState
import java.time.Clock
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val applicationScope: ApplicationScope,
    val windowState: WindowState
) {

    val timerFlow = generateSequence { Clock.systemUTC().instant().toEpochMilli() }
        .map { it / 1000 }
        .distinct()
        .withIndex()
        .map { it.index }
        .asFlow()
        .onCompletion { println("onCompletion: ${Thread.currentThread().name}") }

    val lifecycleFlow = MutableStateFlow<Lifecycle>(Lifecycle.Initializing).apply {
        this.onEach {
            if (it == Lifecycle.Stopping) {
                exitApplication()
            }
        }
            .launchIn(CoroutineScope(EmptyCoroutineContext))
    }

    private val _text = mutableStateOf("Hello, World!")
    val text = derivedStateOf { _text.value }

    private val _counter = mutableStateOf(0)
    val counter = derivedStateOf { _counter.value }

    fun updateText(text: String) {
        _text.value = text
    }

    fun incrementCounter() {
        _counter.value++
    }

    fun stopApplication() {
        lifecycleFlow.value = Lifecycle.Stopping
    }

    private fun exitApplication() {
        applicationScope.exitApplication()
    }
}
