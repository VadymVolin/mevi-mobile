package com.mevi.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mevi.ui.theme.MeviTheme

@Composable
fun MeviSelectorButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
        modifier = modifier then Modifier
            .fillMaxWidth()
            .height(56.dp)

    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                imageVector = icon
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            contentDescription = null,
            imageVector = Icons.Default.KeyboardArrowDown
        )
    }
}

@Preview(
    showBackground = true, device = Devices.PIXEL_3, showSystemUi = true
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun MeviSelectorButtonPreview() {
    MeviTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 20.dp)) {
                MeviSelectorButton(
                    onClick = { /*TODO*/ },
                    text = "Preview",
                    icon = Icons.Default.Language
                )
            }
        }
    }
}