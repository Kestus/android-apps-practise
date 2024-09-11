package com.kes.app039_kt_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.kes.app039_kt_notes.MainActivity
import com.kes.app039_kt_notes.R
import com.kes.app039_kt_notes.adapters.NoteAdapter
import com.kes.app039_kt_notes.databinding.FragmentHomeBinding
import com.kes.app039_kt_notes.databinding.FragmentNewNoteBinding
import com.kes.app039_kt_notes.models.Note
import com.kes.app039_kt_notes.viewmodel.NoteViewModel


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel
        this.view = view
        binding.fabSave.setOnClickListener {
            saveNote(view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveNote(view: View) {
        val title = binding.cardInputTitle.text.toString().trim()
        val body = binding.cardInputBody.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(view.context, "Please enter note title!", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(0, title, body)
        viewModel.addNote(note)
        Toast.makeText(view.context, "Saved!", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
//        inflater.inflate(R.menu.home_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}