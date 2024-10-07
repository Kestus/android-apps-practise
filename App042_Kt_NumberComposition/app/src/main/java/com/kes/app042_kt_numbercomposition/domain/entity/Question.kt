package com.kes.app042_kt_numbercomposition.domain.entity

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val correctAnswer get() = sum - visibleNumber
}