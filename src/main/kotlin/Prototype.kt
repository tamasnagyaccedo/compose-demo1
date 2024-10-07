import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import kotlin.reflect.KProperty

fun <T> Module.prototype(factory: () -> T): Prototype<T> = Prototype(this, factory)

class Prototype<T>(private val module: Module, private val factory: () -> T) {
    private fun instantiate(): T = factory()

    operator fun getValue(t: Any?, property: KProperty<*>): T {
        return instantiate()
    }
}

@Composable
fun <T> ProvidableCompositionLocal<Prototype<T>>.prototype() = current
