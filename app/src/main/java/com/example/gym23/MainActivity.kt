package com.example.gym23

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.example.gym23.Entity.UserRecord
import com.example.gym23.screens.*
import com.example.gym23.ui.theme.GYM23Theme
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializar Firebase App Check en modo Debug
        FirebaseApp.initializeApp(this)
        val appCheck = FirebaseAppCheck.getInstance()
        appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )

        setContent {
            GYM23Theme {
                var currentScreen by remember { mutableStateOf("signup") }
                var loggedInUser by remember { mutableStateOf<UserRecord?>(null) }

                when (currentScreen) {
                    "signup" -> SignUpScreen(
                        onSignInNavigate = { currentScreen = "signin" },
                        onSignUpSuccess = {
                            loggedInUser = it
                            currentScreen = "home"
                        }
                    )

                    "signin" -> SignInScreen(
                        onSignUpNavigate = { currentScreen = "signup" },
                        onSignInSuccess = {
                            loggedInUser = it
                            currentScreen = "home"
                        }
                    )

                    "home" -> {
                        loggedInUser?.let {
                            HomeScreen(user = it)
                        }
                    }
                }
            }
        }
    }
}
