import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import kotlin.reflect.KProperty

class Singleton<T>(private val module: Module, factory: () -> T) {
    private val instance: T by lazy(factory)

    operator fun getValue(t: Any?, property: KProperty<*>): T {
        return instance
    }
}

fun <T> Module.singleton(factory: () -> T): Singleton<T> = Singleton(this, factory)

@Composable
fun <T> ProvidableCompositionLocal<Singleton<T>>.singleton() = current
