package com.kes.app042_kt_numbercomposition.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kes.app042_kt_numbercomposition.R
import com.kes.app042_kt_numbercomposition.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    private val args by navArgs<GameResultFragmentArgs>()
    private val gameResult get() = args.gameResult

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

        setRetryGameListener()
        bindImage()
        bindData()
    }

    private fun bindImage() {
        val imgSrc = if (gameResult.winner) R.drawable.ic_smile
        else R.drawable.ic_frown
        binding.emojiResult.setImageResource(imgSrc)
    }

    private fun bindData() {
        binding.gameResult = gameResult
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRetryGameListener() {
        binding.btnRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}