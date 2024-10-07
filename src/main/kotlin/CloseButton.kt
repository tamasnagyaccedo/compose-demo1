import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TopBar.CloseButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val hoverState by interactionSource.collectIsHoveredAsState()

    Icon(
        Icons.Default.Close,
        "Close",
        tint = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .hoverable(interactionSource)
            .background(if (hoverState) Color.Red else Color.Gray)
            .clickable {
                onClose()
            }
    )
}
