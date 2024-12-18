package com.kes.app049_kt_coroutineflow.teamScore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamScoreViewModel : ViewModel() {

    private val _state = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))
    val state = _state.asStateFlow()

    fun increaseScore(team: Team) {
        val currentState = _state.value
        if (currentState !is TeamScoreState.Game) return
        val newScore: Int
        when (team) {
            Team.TEAM_1 -> {
                newScore = currentState.score1.plus(1)
                _state.value = currentState.copy(score1 = newScore)
            }

            Team.TEAM_2 -> {
                newScore = currentState.score2.plus(1)
                _state.value = currentState.copy(score2 = newScore)
            }
        }
        if (newScore >= WINNER_SCORE) {
            finish(team)
        }
    }

    private fun finish(team: Team) {
        _state.value = TeamScoreState.Winner(
            team,
            state.value.score1,
            state.value.score2
        )
    }

    companion object {
        private const val WINNER_SCORE = 5
    }

}