package com.kes.app039_kt_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.kes.app039_kt_notes.MainActivity
import com.kes.app039_kt_notes.R
import com.kes.app039_kt_notes.databinding.FragmentNewNoteBinding
import com.kes.app039_kt_notes.databinding.FragmentUpdateNoteBinding
import com.kes.app039_kt_notes.models.Note
import com.kes.app039_kt_notes.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var view: View
    private lateinit var currentNote: Note

    // Update Note Fragment contains arguments
    private val args: UpdateNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!


        binding.cardInputTitle.setText(currentNote.title)
        binding.cardInputBody.setText(currentNote.body)

        binding.fabSave.setOnClickListener {
            updateNote(view)
        }

        binding.fabDelete.setOnClickListener {
            deleteNote(view)
        }
    }

    private fun updateNote(view: View) {
        val title = binding.cardInputTitle.text.toString().trim()
        val body = binding.cardInputBody.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(view.context, "Title should not be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        currentNote.title = title
        currentNote.body = body
        viewModel.updateNote(currentNote)
        Toast.makeText(view.context, "Saved!", Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
    }

    private fun deleteNote(view: View) {
        AlertDialog.Builder(view.context).apply {
            setTitle("Delete Note")
            setMessage("Delete this note?")

            setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(currentNote)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }

            setNegativeButton("Cancel", null)
        }.create().show()
    }

}