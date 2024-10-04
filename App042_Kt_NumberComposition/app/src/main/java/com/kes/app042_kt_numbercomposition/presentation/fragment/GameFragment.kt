package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.data.GameRepositoryImpl
import com.kes.app042_kt_numbercomposition.databinding.FragmentGameBinding
import com.kes.app042_kt_numbercomposition.domain.entity.GameResult
import com.kes.app042_kt_numbercomposition.domain.entity.Level
import com.kes.app042_kt_numbercomposition.domain.repository.GameRepository

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private lateinit var level: Level

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
        binding.apply {
            tvSum.setOnClickListener {
                launchResultFragment(
                    GameResult(
                        true,
                        2,
                        4,
                        GameRepositoryImpl.getGameSettings(level)
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        level = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(KEY_LEVEL, Level::class.java)!!
        } else {
            requireArguments().getSerializable(KEY_LEVEL) as Level
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
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}