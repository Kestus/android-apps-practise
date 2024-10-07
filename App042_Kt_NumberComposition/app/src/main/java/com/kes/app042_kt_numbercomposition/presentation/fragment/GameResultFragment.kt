package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.databinding.FragmentGameResultBinding
import com.kes.app042_kt_numbercomposition.domain.entity.GameResult

class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addOnBackPressedCallback()
        binding.btnRetry.setOnClickListener { retryGame() }
        bindImage()
        bindTextViews()
    }

    private fun bindImage() {
        val imgSrc = if (gameResult.winner) R.drawable.ic_smile
        else R.drawable.ic_frown
        binding.emojiResult.setImageResource(imgSrc)
    }

    private fun bindTextViews() {
        binding.apply {
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_answers),
                gameResult.gameSettings.minCountOfRightAnswers
            )

            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfCorrectAnswers
            )

            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )

            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                gameResult.percentOfCorrectAnswers
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_RESULT, GameResult::class.java)?.let {
                gameResult = it
            }
        } else {
            requireArguments().getParcelable<GameResult>(KEY_RESULT)?.let {
                gameResult = it
            }
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        private const val KEY_RESULT = "result"

        fun newInstance(result: GameResult): GameResultFragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_RESULT, result)
                }
            }
        }
    }
}