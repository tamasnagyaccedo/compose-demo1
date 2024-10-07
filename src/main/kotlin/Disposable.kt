import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.DisposableHandle

class Disposable(private val name: String, private val cleanUp : () -> Unit = {}) {
    private var state: Boolean by mutableStateOf(false)
    private val delegate = DisposableHandle {
        cleanUp()
        state = true
    }

    fun dispose() {
        if (!state) {
            println("$name disposing...")
            delegate.dispose()
        }
    }

    fun isDisposed() = state
}
