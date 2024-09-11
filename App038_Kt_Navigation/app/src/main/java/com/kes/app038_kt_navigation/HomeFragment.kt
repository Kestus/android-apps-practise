package com.kes.app038_kt_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kes.app038_kt_navigation.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.btnSubmit.setOnClickListener() {
            val input: String = binding.input.text.toString()

            if (input.isEmpty()) {
                Toast.makeText(context, "Enter your name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bundle = bundleOf("name" to input)
            it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
        }

        return binding.root
    }


}