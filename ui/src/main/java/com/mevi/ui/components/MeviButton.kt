package com.mevi.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MeviButton(onClick: () -> Unit, text: String) {
    FilledIconButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.padding(24.dp, 0.dp)
        )
    }
}