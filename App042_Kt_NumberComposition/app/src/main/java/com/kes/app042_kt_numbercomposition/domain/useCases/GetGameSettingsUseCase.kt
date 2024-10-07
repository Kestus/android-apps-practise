package com.kes.app042_kt_numbercomposition.domain.useCases

import com.kes.app042_kt_numbercomposition.domain.entity.GameSettings
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.repository.GameRepository

class GetGameSettingsUseCase (
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}