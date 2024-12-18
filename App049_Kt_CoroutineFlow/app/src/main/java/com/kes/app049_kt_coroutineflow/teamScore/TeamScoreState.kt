package com.kes.app049_kt_coroutineflow.teamScore

sealed class TeamScoreState(
    open val score1: Int,
    open val score2: Int
) {

    data class Game(
        override val score1: Int,
        override val score2: Int
    ) : TeamScoreState(score1, score2)

    data class Winner(
        val winner: Team,
        override val score1: Int,
        override val score2: Int
    ) : TeamScoreState(score1, score2)

}