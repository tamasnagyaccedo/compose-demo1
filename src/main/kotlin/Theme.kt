import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val light = lightColors(
    primary = Color.Blue,
    secondary = Color.Green,
    background = Color.White,
    onBackground = Color.Black,
)

private val dark = darkColors(
    primary = Color.LightGray,
    secondary = Color.LightGray,
    background = Color.DarkGray,
    onBackground = Color.LightGray,
)

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) dark else light

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
