package com.mevi.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun MeviIconButton(modifier: Modifier = Modifier, onClick: () -> Unit, icon: Int) {
    IconButton(
        onClick = onClick, modifier = Modifier
            .size(32.dp) then modifier
    ) {
        Icon(
            ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}