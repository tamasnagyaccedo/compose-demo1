import androidx.compose.runtime.CompositionServiceKey

sealed interface ServiceKeys<T> : CompositionServiceKey<T> {

    interface SomeServiceKey : ServiceKeys<SomeService> {
        companion object : SomeServiceKey
    }

    interface ExampleServiceKey : ServiceKeys<ExampleService> {
        companion object : ExampleServiceKey
    }
}
