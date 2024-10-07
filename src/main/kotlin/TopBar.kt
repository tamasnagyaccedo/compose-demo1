import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class TopBar(
    val title: String = "",
    val onClose: () -> Unit,
    val onMinimize: () -> Unit,
    val onMaximize: () -> Unit,
)

@Composable
fun TopBar(
    title: String = "",
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onMinimize: () -> Unit = {},
    onMaximize: () -> Unit = {},
) {
    with(TopBar(
        title = title,
        onClose = onClose,
        onMinimize = onMinimize,
        onMaximize = onMaximize,
    )) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Gray),
        ) {

            Row(
                modifier = modifier
                    .height(40.dp)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {

                Text(
                    title,
                    color = MaterialTheme.colors.onBackground,
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    Row(modifier = Modifier.align(Alignment.End)) {

                        MinimizeButton()
                        Spacer(modifier = Modifier.width(4.dp))
                        MaximizeButton()
                        Spacer(modifier = Modifier.width(4.dp))
                        CloseButton()
                    }
                }
            }
        }
    }
}
