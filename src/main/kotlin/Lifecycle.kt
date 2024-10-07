sealed interface Lifecycle {
    data object Initializing : Lifecycle
    data object Initialized : Lifecycle
    data object Destroyed : Lifecycle
    data object Started : Lifecycle
    data object Stopping : Lifecycle
    data object Stopped : Lifecycle
    data object Failed : Lifecycle
}
