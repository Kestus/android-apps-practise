package com.kes.app048_kt_factorial

sealed class State

class Error(val message: String): State()
class Factorial(val value: String): State()
data object Progress : State()
