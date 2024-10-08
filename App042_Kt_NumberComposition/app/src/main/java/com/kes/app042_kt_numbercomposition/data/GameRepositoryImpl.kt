package com.kes.app042_kt_numbercomposition.data

import com.kes.app042_kt_numbercomposition.domain.entity.GameSettings
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.entity.Question
import com.kes.app042_kt_numbercomposition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 7
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val correctAnswer = sum - visibleNumber
        options.add(correctAnswer)
        val from = max(correctAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val until = min( correctAnswer + countOfOptions, sum)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, until))
        }

        return Question(sum, visibleNumber, options.shuffled())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            Level.EASY -> GameSettings(
                10,
                10,
                50,
                60
            )
            Level.MEDIUM -> GameSettings(
                99,
                15,
                70,
                50
            )
            Level.HARD -> GameSettings(
                999,
                20,
                90,
                40
            )
        }
    }

}