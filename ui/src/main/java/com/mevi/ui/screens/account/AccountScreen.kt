package com.mevi.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth
import com.mevi.ui.navigation.Route


@Composable
fun AccountScreen(onLogout: () -> Unit) {
    Column {
        Text(text = Route.Menu.ROUTE_SCREEN_ACCOUNT.route)
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            onLogout()
        }) {
            Text(text = "LOGOUT")
        }
    }
}