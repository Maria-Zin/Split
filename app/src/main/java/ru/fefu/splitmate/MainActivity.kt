package com.example.splitmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.splitmate.ui.screens.*
import com.example.splitmate.ui.viewmodels.SharedViewModel

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
                    val viewModel: SharedViewModel = viewModel()

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
                            InputScreen(
                                viewModel = viewModel,
                                onCalculateClick = { total, people, tip ->
                                    val calculation = viewModel.addCalculation(total, people, tip)
                                    navController.navigate("result/${calculation.id}")
                                }
                            )
                        }

                        composable(
                            route = "result/{calcId}",
                            arguments = listOf(
                                navArgument("calcId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val calcId = backStackEntry.arguments?.getString("calcId") ?: ""

                            val calculation = viewModel.getCalculationById(calcId)

                            if (calculation != null) {
                                ResultScreen(
                                    total = calculation.total,
                                    people = calculation.people,
                                    tipAmount = calculation.tip,
                                    onBackToEdit = {
                                        navController.popBackStack()
                                    },
                                    onNewCalculation = {
                                        viewModel.resetCurrent()
                                        navController.navigate("input") {
                                            popUpTo("input") { inclusive = true }
                                        }
                                    }
                                )
                            } else {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}