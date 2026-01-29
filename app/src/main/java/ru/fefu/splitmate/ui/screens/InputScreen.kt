package com.example.splitmate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.splitmate.ui.viewmodels.SharedViewModel // Добавьте этот импорт

@Composable
fun InputScreen(
    viewModel: SharedViewModel,
    onCalculateClick: (total: Double, people: Int, tipAmount: Double) -> Unit
) {
    var totalInput by remember { mutableStateOf("") }
    var peopleInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }

    LaunchedEffect(totalInput, peopleInput, tipInput) {
        viewModel.updateInputs(totalInput, peopleInput, tipInput)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = totalInput,
            onValueChange = { totalInput = it },
            label = { Text("Total amount") },
            placeholder = { Text("1000") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = peopleInput,
            onValueChange = { peopleInput = it },
            label = { Text("Number of people") },
            placeholder = { Text("4") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = { Text("Tip amount") },
            placeholder = { Text("100") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val total = totalInput.toDoubleOrNull() ?: 0.0
                val people = peopleInput.toIntOrNull() ?: 1
                val tip = tipInput.toDoubleOrNull() ?: 0.0
                onCalculateClick(total, people, tip)
            },
            enabled = viewModel.isCalculateEnabled.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }
    }
}