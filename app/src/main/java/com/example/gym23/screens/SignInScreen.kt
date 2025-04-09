package com.example.gym23.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gym23.Entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onSignUpNavigate: () -> Unit,
    onSignInSuccess: (UserRecord) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showLoader) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    showLoader = true
                    // Simular validación en backend
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        showLoader = false
                        val fakeUser = UserRecord.fake(email, "999999999")
                        onSignInSuccess(fakeUser)
                    }

                } else {
                    Toast.makeText(context, "Correo y contraseña requeridos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Iniciar sesión")
            }
        }

        TextButton(onClick = onSignUpNavigate) {
            Text("¿No tenés cuenta? Registrate")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        onSignUpNavigate = {},
        onSignInSuccess = {}
    )
}
