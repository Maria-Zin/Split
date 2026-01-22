package com.example.splitmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splitmate.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "welcome"
                    ) {
                        composable("welcome") {
                            WelcomeScreen(
                                onStartClick = {
                                    navController.navigate("input")
                                }
                            )
                        }

                        composable("input") {
                            InputScreen { total, people, tipAmount ->
                                val calcId = System.currentTimeMillis().toString()
                                navController.navigate("result/$calcId?total=$total&people=$people&tip=$tipAmount")
                            }
                        }

                        composable(
                            route = "result/{calcId}?total={total}&people={people}&tip={tip}",
                            arguments = listOf(
                                navArgument("calcId") {
                                    type = NavType.StringType
                                },
                                navArgument("total") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("people") {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument("tip") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) { backStackEntry ->
                            val totalArg = backStackEntry.arguments?.getString("total")?.toDoubleOrNull() ?: 0.0
                            val peopleArg = backStackEntry.arguments?.getString("people")?.toIntOrNull() ?: 1
                            val tipArg = backStackEntry.arguments?.getString("tip")?.toDoubleOrNull() ?: 0.0
                            val calcId = backStackEntry.arguments?.getString("calcId") ?: ""

                            ResultScreen(
                                total = totalArg,
                                people = peopleArg,
                                tipAmount = tipArg,
                                onBackToEdit = {
                                    navController.popBackStack()
                                },
                                onNewCalculation = {
                                    navController.navigate("welcome") {
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
