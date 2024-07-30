import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mevi.ui.components.HighlightedText

@Composable
fun DialogListItem(
    icon: String,
    itemText: String,
    searchString: String? = "",
    onClick: () -> Unit,
    selectedItem: String?
) {
    val isSelected = selectedItem == itemText
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier, text = icon, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(12.dp))
            HighlightedText(
                modifier = Modifier.weight(1f),
                fullText = itemText,
                searchString = searchString ?: ""
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    }

}

@Composable
fun DialogListItem(
    icon: ImageVector,
    itemText: String,
    searchString: String? = "",
    onClick: () -> Unit,
    selectedItem: String?
) {
    val isSelected = selectedItem == itemText
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                imageVector = icon
            )
            Spacer(modifier = Modifier.width(12.dp))
            HighlightedText(
                modifier = Modifier.weight(1f),
                fullText = itemText,
                searchString = searchString ?: ""
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    }

}