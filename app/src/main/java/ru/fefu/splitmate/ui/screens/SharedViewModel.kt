package com.example.splitmate.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Calculation(
    val id: String,
    val total: Double,
    val people: Int,
    val tip: Double
)

class SharedViewModel : ViewModel() {
    private val _calculations = mutableListOf<Calculation>()
    val calculations: List<Calculation> get() = _calculations

    private val _currentCalculation = MutableStateFlow<Calculation?>(null)
    val currentCalculation: StateFlow<Calculation?> = _currentCalculation.asStateFlow()

    fun addCalculation(total: Double, people: Int, tip: Double) {
        val id = System.currentTimeMillis().toString()
        val calc = Calculation(id, total, people, tip)
        _calculations.add(calc)
        _currentCalculation.value = calc
    }

    fun getCalculationById(id: String): Calculation? {
        return _calculations.find { it.id == id }
    }

    fun resetCurrent() {
        _currentCalculation.value = null
    }
}