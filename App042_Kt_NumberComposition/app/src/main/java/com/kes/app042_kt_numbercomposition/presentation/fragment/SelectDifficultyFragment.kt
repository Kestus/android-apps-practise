package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kes.app042_kt_numbercomposition.databinding.FragmentDifficultySelectBinding
import com.kes.app042_kt_numbercomposition.domain.entity.Level

class SelectDifficultyFragment : Fragment() {

    private var _binding: FragmentDifficultySelectBinding? = null
    private val binding: FragmentDifficultySelectBinding
        get() = _binding ?: throw RuntimeException("FragmentDifficultySelectBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDifficultySelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSelectionClickListeners()
    }

    private fun addSelectionClickListeners() {
        binding.apply {
            btnTest.setOnClickListener { launchGameFragment(Level.TEST) }
            btnEasy.setOnClickListener { launchGameFragment(Level.EASY) }
            btnMedium.setOnClickListener { launchGameFragment(Level.MEDIUM) }
            btnHard.setOnClickListener { launchGameFragment(Level.HARD) }
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            SelectDifficultyFragmentDirections.actionDifficultySelectFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}