import androidx.compose.runtime.CompositionServiceKey
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class SomeService : ServiceKeys.SomeServiceKey, CompositionServiceKey<SomeService> {

    private val scope = CoroutineScope(EmptyCoroutineContext)

    val channel = channelFlow<String> {
        println("$TAG channel opened")

        awaitClose {
            runBlocking {
                println("$TAG channel closed")
                channel.close()
            }
        }
    }
        .onCompletion { println("$TAG stopped") }
        .stateIn(scope, SharingStarted.WhileSubscribed(), "initializing")

    fun start() {
        println("$TAG started")
    }

    fun stop() {
        scope.cancel()
        println("$TAG stopping")
    }

    companion object {
        const val TAG = "SomeService"
    }
}
