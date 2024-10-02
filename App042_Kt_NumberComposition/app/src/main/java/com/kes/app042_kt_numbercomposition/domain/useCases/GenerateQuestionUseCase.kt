package com.kes.app042_kt_numbercomposition.domain.useCases

import com.kes.app042_kt_numbercomposition.domain.entity.Question
import com.kes.app042_kt_numbercomposition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}