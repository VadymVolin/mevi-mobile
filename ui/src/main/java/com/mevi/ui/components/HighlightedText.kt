package com.mevi.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun HighlightedText(modifier: Modifier = Modifier, fullText: String, searchString: String) {
    val defaultStyle = SpanStyle(color = MaterialTheme.colorScheme.onBackground)
    val highlightStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
    val highlightedText = buildAnnotatedString {
        val startIndex = fullText.indexOf(searchString, 0, ignoreCase = true)
        val endIndex = startIndex + searchString.length
        if (startIndex == -1) {
            append(fullText.substring(startIndex))
            return
        }
        if (startIndex > 0) {
            withStyle(defaultStyle) {
                append(fullText.substring(0, startIndex))
            }
        }
        withStyle(highlightStyle) {
            append(fullText.substring(startIndex, endIndex))
        }
        if (endIndex < fullText.length) {
            withStyle(defaultStyle) {
                append(fullText.substring(endIndex, fullText.length))
            }
        }

    }
    Text(
        modifier = modifier,
        text = highlightedText,
        style = MaterialTheme.typography.titleMedium
    )
}