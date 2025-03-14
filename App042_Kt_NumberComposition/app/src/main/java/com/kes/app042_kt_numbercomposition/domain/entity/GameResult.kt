package com.kes.app042_kt_numbercomposition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult (
    val winner: Boolean,
    val countOfCorrectAnswers: Int,
    val percentOfCorrectAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable {
}