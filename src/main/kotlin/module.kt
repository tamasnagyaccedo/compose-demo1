import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.launchApplication
import kotlinx.coroutines.CoroutineScope

interface Module {
    @Composable
    fun Module.declare(factory: () -> ProvidedValue<*>)
    @Composable
    fun Module.content(builder: @Composable () -> Unit)
}

internal data class ModuleImpl(
    val name: String,
) : Module {
    private val providedValues = mutableListOf<ProvidedValue<*>>()

    @Composable
    override fun Module.declare(factory: () -> ProvidedValue<*>) {
        providedValues += factory()
    }

    @Composable
    override fun Module.content(builder: @Composable () -> Unit) = CompositionLocalProvider(
        *providedValues.toTypedArray(),
    ) {
        builder()
    }
}

@Composable
fun ExtendedApplicationScope.module(name: String = this.name, builder: @Composable Module.() -> Unit) {
    builder(ModuleImpl(name))
}

class ExtendedApplicationScope(
    val name: String,
    private val scope: ApplicationScope,
) : ApplicationScope by scope

fun CoroutineScope.awaitApplication(name: String, block: @Composable ExtendedApplicationScope.() -> Unit) {
    launchApplication {
        ExtendedApplicationScope(name, this).block()
    }
}
