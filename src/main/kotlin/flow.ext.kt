import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.takeWhile

inline fun <reified T> Flow<T>.takeUntil(crossinline predicate: (T) -> Boolean) =
    takeWhile { !predicate(it) }
