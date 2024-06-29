package com.mevi.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mevi.ui.theme.MeviTheme


@Composable
fun MeviIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Int,
    iconColor: Color = LocalContentColor.current
) {
    MeviIconButton(modifier, onClick, ImageVector.vectorResource(icon), iconColor)
}

@Composable
fun MeviIconButton(
    modifier: Modifier = Modifier, onClick: () -> Unit, icon: ImageVector, iconColor: Color = MaterialTheme.colorScheme.onSurface
) {
    IconButton(
        onClick = onClick, modifier = modifier then Modifier.size(32.dp)
    ) {
        Icon(
            icon, contentDescription = null, tint = iconColor
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
fun MeviIconButtonPreview() {
    MeviTheme {
        Surface {
            MeviIconButton(onClick = { /*TODO*/ }, icon = Icons.Filled.CheckBox)
        }
    }
}