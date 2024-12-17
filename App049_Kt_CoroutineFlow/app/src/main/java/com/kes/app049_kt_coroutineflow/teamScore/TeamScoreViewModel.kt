package com.kes.app049_kt_coroutineflow.teamScore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamScoreViewModel : ViewModel() {


    private val _state = MutableLiveData<TeamScoreState>(TeamScoreState.Game(0, 0))
    val state: LiveData<TeamScoreState> get() = _state

    fun increaseScore(team: Team) {
        val currentState =
            if (_state.value is TeamScoreState.Game) {
                _state.value as TeamScoreState.Game
            } else return
        when (team) {
            Team.TEAM_1 -> {
                val newScore = currentState.score1.plus(1)
                _state.value = currentState.copy(score1 = newScore)
                if (newScore >= WINNER_SCORE) {
                    finish(Team.TEAM_1)
                }
            }

            Team.TEAM_2 -> {
                val newScore = currentState.score2.plus(1)
                _state.value = currentState.copy(score2 = newScore)
                if (newScore >= WINNER_SCORE) {
                    finish(Team.TEAM_2)
                }
            }
        }
    }

    private fun finish(team: Team) {
        _state.value?.let {
            _state.value = TeamScoreState.Winner(
                team,
                it.score1,
                it.score2
            )
        }
    }

    companion object {
        private const val WINNER_SCORE = 5
    }

}