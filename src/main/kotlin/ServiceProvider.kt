import androidx.compose.runtime.CompositionServiceKey
import androidx.compose.runtime.CompositionServices

object ServiceProvider : CompositionServices {

    private val someService: SomeService by lazy { SomeService() }
    private val exampleService: ExampleService by lazy { ExampleService(someService) }

    override fun <T> getCompositionService(key: CompositionServiceKey<T>): T? {

        return when (key) {
            is ServiceKeys -> when (key) {
                is ServiceKeys.SomeServiceKey -> someService as T
                is ServiceKeys.ExampleServiceKey -> exampleService as T
            }

            else -> null
        }
    }

    fun <T> getService(key: ServiceKeys<T>): T? = getCompositionService(key)
}
