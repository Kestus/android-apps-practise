package com.kes.app042_kt_numbercomposition.domain.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.data.GameRepositoryImpl
import com.kes.app042_kt_numbercomposition.domain.entity.GameResult
import com.kes.app042_kt_numbercomposition.domain.entity.GameSettings
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.entity.Question
import com.kes.app042_kt_numbercomposition.domain.useCases.GenerateQuestionUseCase
import com.kes.app042_kt_numbercomposition.domain.useCases.GetGameSettingsUseCase
import java.util.Locale

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings

    private var countOfCorrectAnswers = 0
    private var countOfQuestion = 0

    private var timer: CountDownTimer? = null
    private val _formattedTimer = MutableLiveData<String>()
    val formattedTimer: LiveData<String>
        get() = _formattedTimer

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfCorrectAnswers = MutableLiveData<Int>()
    val percentOfCorrectAnswers: LiveData<Int>
        get() = _percentOfCorrectAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercentage = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercentage

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> get() = _gameResult


    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(this.level)
        this._minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTimer.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val totalSeconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = totalSeconds / SECONDS_IN_MINUTES
        val seconds = totalSeconds - (minutes * SECONDS_IN_MINUTES)
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val correctAnswer = _question.value?.correctAnswer
        if (number == correctAnswer) {
            countOfCorrectAnswers++
        }
        countOfQuestion++
    }

    private fun updateProgress() {
        val percent = calculatePercentage()
        _percentOfCorrectAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfCorrectAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfCorrectAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentage.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentage(): Int {
        return ((countOfCorrectAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfCorrectAnswers,
            percentOfCorrectAnswers.value?: 0,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}