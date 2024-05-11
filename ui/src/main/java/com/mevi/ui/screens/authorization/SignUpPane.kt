package com.mevi.ui.screens.authorization


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.ui.screens.state.UIScreenState


@Composable
fun SignUpPane(
    registrationState: UIScreenState<MeviUser>,
    registerAction: (Pair<String, String>) -> Unit // TODO: modify later
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Sign up")
    }
}


//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO)
//@Preview(
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    device = Devices.PIXEL_6_PRO
//)
//@Composable
//fun SimpleComposablePreview() {
//    MeviTheme {
//
//        SignIn()
//    }
//}