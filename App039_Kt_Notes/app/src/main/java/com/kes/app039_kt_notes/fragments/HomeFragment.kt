package com.kes.app039_kt_notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kes.app039_kt_notes.MainActivity
import com.kes.app039_kt_notes.R
import com.kes.app039_kt_notes.adapters.NoteAdapter
import com.kes.app039_kt_notes.databinding.FragmentHomeBinding
import com.kes.app039_kt_notes.models.Note
import com.kes.app039_kt_notes.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel

        initRecyclerView()
        binding.fabAdd.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homeFragment_to_newNoteFragment
            )
        }
        
        binding.searchInput.addTextChangedListener() { text ->
            if (text == null) {
                return@addTextChangedListener
            }

            searchNote(text.toString().trim())
        }
    }

    private fun initRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            viewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) { notes ->
                noteAdapter.differ.submitList(notes)
                updateUI(notes)
            }

        }
    }

    private fun updateUI(notes: List<Note>) {
        if (notes.isNotEmpty()) {
            binding.noNotesCard.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.noNotesCard.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
//        searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query"
        viewModel.searchNote(searchQuery).observe(viewLifecycleOwner) {
            list -> noteAdapter.differ.submitList(list)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
//        inflater.inflate(R.menu.home_menu, menu)
//        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
//        menuSearch.isSubmitButtonEnabled = true
//        menuSearch.setOnQueryTextListener(this)
//    }
}