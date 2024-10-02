package com.kes.app042_kt_numbercomposition.domain.repository

import com.kes.app042_kt_numbercomposition.domain.entity.GameSettings
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

}