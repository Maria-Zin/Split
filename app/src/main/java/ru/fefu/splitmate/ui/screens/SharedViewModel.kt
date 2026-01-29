package com.example.splitmate.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Calculation(
    val id: String,
    val total: Double,
    val people: Int,
    val tip: Double
)

data class InputState(
    val total: String = "",
    val people: String = "",
    val tip: String = ""
)

class SharedViewModel : ViewModel() {
    private val _calculations = mutableStateListOf<Calculation>()
    val calculations: List<Calculation> get() = _calculations

    var currentCalculation: Calculation? by mutableStateOf(null)
        private set

    var inputState by mutableStateOf(InputState())
        private set

    val isCalculateEnabled = mutableStateOf(false)

    fun updateInputs(total: String, people: String, tip: String) {
        inputState = InputState(total, people, tip)

        val isEnabled = total.toDoubleOrNull() != null &&
                total.toDoubleOrNull()!! >= 0 &&
                people.toIntOrNull() != null &&
                people.toIntOrNull()!! > 0 &&
                tip.toDoubleOrNull() != null &&
                tip.toDoubleOrNull()!! >= 0

        isCalculateEnabled.value = isEnabled
    }

    fun addCalculation(total: Double, people: Int, tip: Double): Calculation {
        val id = System.currentTimeMillis().toString()
        val calc = Calculation(id, total, people, tip)
        _calculations.add(calc)
        currentCalculation = calc
        return calc
    }

    fun getCalculationById(id: String): Calculation? {
        return _calculations.find { it.id == id }
    }

    fun resetCurrent() {
        currentCalculation = null
        inputState = InputState()
    }
}