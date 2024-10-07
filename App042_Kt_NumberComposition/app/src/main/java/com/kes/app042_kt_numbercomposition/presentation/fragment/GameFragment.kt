package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.databinding.FragmentGameBinding
import com.kes.app042_kt_numbercomposition.domain.entity.GameResult
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.viewmodel.GameViewModel
import com.kes.app042_kt_numbercomposition.domain.viewmodel.GameViewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion as AndroidViewModelFactory

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions: MutableList<TextView> by lazy {
        mutableListOf<TextView>().apply {
            binding.apply {
                add(tvOption1)
                add(tvOption2)
                add(tvOption3)
                add(tvOption4)
                add(tvOption5)
                add(tvOption6)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setOptionsOnClickListeners()
    }

    private fun observeViewModel() {
        observeTimer()
        observeQuestion()
        observeProgress()
        observeResult()
    }

    private fun setOptionsOnClickListeners() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeTimer() {
        viewModel.formattedTimer.observe(viewLifecycleOwner) {
            binding.apply {
                tvTimer.text = it
            }
        }
    }

    private fun observeQuestion() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.apply {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()

                for ((index, option) in it.options.withIndex()) {
                    val tv = tvOptions[index]
                    tv.text = option.toString()
                }
            }
        }
    }

    private fun observeProgress() {
        viewModel.apply {
            binding.apply {
                // progress text
                progressAnswers.observe(viewLifecycleOwner) {
                    tvAnswersProgress.text = it
                }

                enoughCount.observe(viewLifecycleOwner) {
                    tvAnswersProgress.setTextColor(getProgressColor(it))
                }

                // progress percent
                percentOfCorrectAnswers.observe(viewLifecycleOwner) {
                    progressBar.setProgress(it, true)
                }

                enoughPercent.observe(viewLifecycleOwner) {
                    progressBar.progressTintList = getProgressColor(it)
                }

                // minimal percent
                minPercent.observe(viewLifecycleOwner) {
                    progressBar.secondaryProgress = it
                }
            }
        }
    }

    private fun getProgressColor(enough: Boolean): ColorStateList {
        val colorId = if (enough) R.color.green
        else R.color.red
        return ColorStateList.valueOf(resources.getColor(colorId, null))
    }

    private fun observeResult() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchResultFragment(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_LEVEL, Level::class.java)?.let {
                level = it
            }
        } else {
            requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
                level = it
            }
        }
    }

    private fun launchResultFragment(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameResultFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}