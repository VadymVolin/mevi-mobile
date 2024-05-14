package com.mevi.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth
import com.mevi.ui.navigation.NavigationGraphRoute


@Composable
fun AccountScreen() {
    Column {
        Text(text = NavigationGraphRoute.ROUTE_SCREEN_ACCOUNT.route)
        Button(onClick = { FirebaseAuth.getInstance().signOut() }) {
            Text(text = "LOGOUT")
        }
    }
}