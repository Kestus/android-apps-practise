package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.databinding.FragmentWelcomeBinding

class WelcomeFragment: Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            launchDifficultySelectFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchDifficultySelectFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, DifficultySelectFragment.newInstance())
            .addToBackStack(DifficultySelectFragment.NAME)
            .commit()
    }
}