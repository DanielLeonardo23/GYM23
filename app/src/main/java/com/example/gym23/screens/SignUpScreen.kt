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
import com.example.gym23.Entity.UserRecord
import com.example.gym23.Entity.ValidationResult
import com.example.gym23.FormData.UserSignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(
    onSignInNavigate: () -> Unit,
    onSignUpSuccess: (UserRecord) -> Unit
) {
    var tel by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = tel, onValueChange = { tel = it }, label = { Text("Teléfono") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = repassword, onValueChange = { repassword = it },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showLoader) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                val userSignUp = UserSignUp(tel, email, password, repassword)
                val validation = listOf(
                    userSignUp.validateTelephone(),
                    userSignUp.validateEmail(),
                    userSignUp.validatePassword(),
                    userSignUp.validateRePass()
                )

                if (validation.all { it is ValidationResult.Valid }) {
                    showLoader = true
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            showLoader = false
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                val uid = user?.uid ?: return@addOnCompleteListener

                                val userData = hashMapOf(
                                    "email" to email,
                                    "telefono" to tel,
                                    "fechaCreacion" to System.currentTimeMillis()
                                )

                                db.collection("users").document(uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        val firebaseUser = UserRecord.fake(email, tel)
                                        onSignUpSuccess(firebaseUser)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            "Error al guardar en Firestore: ${it.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Error al registrar: ${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Revisá los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Registrarse")
            }
        }

        TextButton(onClick = onSignInNavigate) {
            Text("¿Ya tenés cuenta? Iniciar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onSignInNavigate = {},
        onSignUpSuccess = {}
    )
}
