package com.kes.app049_kt_coroutineflow.teamScore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TeamScoreViewModel : ViewModel() {

    private var currentState: TeamScoreState = TeamScoreState.Game(0, 0)
    private val _stateFlow = MutableSharedFlow<TeamScoreState>(replay = 1)
    val stateFlow = _stateFlow.asSharedFlow()
        .onEach { currentState = it }

    init {
        viewModelScope.launch {
            _stateFlow.emit(currentState)
        }
    }

    fun increaseScore(team: Team) {
        currentState.let { state ->
            if (state !is TeamScoreState.Game) return
            viewModelScope.launch {
                val newScore: Int
                when (team) {
                    Team.TEAM_1 -> {
                        newScore = currentState.score1.plus(1)
                        _stateFlow.emit(state.copy(score1 = newScore))
                    }

                    Team.TEAM_2 -> {
                        newScore = currentState.score2.plus(1)
                        _stateFlow.emit(state.copy(score2 = newScore))
                    }
                }

                if (newScore >= WINNER_SCORE) {
                    finish(Team.TEAM_2)
                }
            }
        }
    }

    private suspend fun finish(team: Team) {
        _stateFlow.emit(
            TeamScoreState.Winner(
                team,
                currentState.score1,
                currentState.score2
            ) as TeamScoreState
        )
    }

    companion object {
        private const val WINNER_SCORE = 5
    }

}