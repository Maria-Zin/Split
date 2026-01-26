package com.example.splitmate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    total: Double,
    people: Int,
    tipAmount: Double,
    onBackToEdit: () -> Unit,
    onNewCalculation: () -> Unit
) {
    val totalWithTip = total + tipAmount
    val perPerson = if (people > 0) totalWithTip / people else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Results", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Total: ${"%.2f".format(total)} ₽")
        Spacer(modifier = Modifier.height(8.dp))

        Text("Tip: ${"%.2f".format(tipAmount)} ₽")
        Spacer(modifier = Modifier.height(8.dp))

        Text("Total with tip: ${"%.2f".format(totalWithTip)} ₽")
        Spacer(modifier = Modifier.height(8.dp))

        Text("Per person: ${"%.2f".format(perPerson)} ₽")
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onBackToEdit) {
                Text("Back to edit")
            }
            Button(onClick = onNewCalculation) {
                Text("New calculation")
            }
        }
    }
}