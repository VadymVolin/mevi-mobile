package com.mevi.ui.components.dialogs

import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeviDialog(
    dialogGravity: Int = Gravity.CENTER,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
//    android:windowSoftInputMode="adjustResize"
//    val density = LocalDensity.current
//    val imeInsets = WindowInsets.ime
//    val imeHeightDp by remember {
//        derivedStateOf {
//            with(density) { imeInsets.getBottom(density).toDp() }
//        }
//    }

    BasicAlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .imePadding()
//            .then(
//                if (android.os.Build.VERSION.SDK_INT < 31) Modifier.padding(bottom = imeHeightDp * 0.85f) else Modifier
//            )
        ,
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = android.os.Build.VERSION.SDK_INT < 31
        )
    ) {
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        dialogWindowProvider?.window?.setGravity(dialogGravity)
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
        ) {
            content()
        }
    }
}