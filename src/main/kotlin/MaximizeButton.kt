import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter

@Composable
fun TopBar.MaximizeButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val hoverState by interactionSource.collectIsHoveredAsState()
    Icon(
        loadSvgPainter(ClassLoader.getSystemResourceAsStream("maximize.svg")!!, LocalDensity.current),
        "Maximize",
        tint = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .hoverable(interactionSource)
            .background(if (hoverState) Color.DarkGray else Color.Gray)
            .clickable {
                onMaximize()
            }
    )
}
