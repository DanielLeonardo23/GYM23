package com.example.gym23.screens
import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.gym23.Entity.*
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable

fun HomeScreen(user: UserRecord) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.entries.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = navController.currentDestination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { IndexScreen(user) }
            composable(BottomNavItem.Profile.route) { ProfileScreen(user) }
            composable(BottomNavItem.Meals.route) { MealScreen(user) }
            composable(BottomNavItem.Trainers.route) { TrainerScreen(user) }
            composable(BottomNavItem.Payment.route) { PaymentScreen(user) }
        }
    }
}

// 👇 Opciones del menú inferior
enum class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Profile("profile", "Perfil", Icons.Default.Person),
    Home("home", "Inicio", Icons.Default.Home),
    Meals("meals", "Comidas", Icons.Default.Fastfood),
    Trainers("trainers", "Entrenadores", Icons.Default.FitnessCenter),
    Payment("payment", "Pagos", Icons.Default.Payments)
}

// 👇 Pantallas de ejemplo que podés reemplazar por tus Fragmentos en Compose

@Composable
fun IndexScreen(user: UserRecord) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Bienvenido, ${user.record.email}", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ProfileScreen(user: UserRecord) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Perfil de usuario: ${user.record.telephone}")
    }
}

@Composable
fun MealScreen(user: UserRecord) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla de comidas")
    }
}

@Composable
fun TrainerScreen(user: UserRecord) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla de entrenadores")
    }
}

@Composable
fun PaymentScreen(user: UserRecord) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla de pagos")
    }
}
