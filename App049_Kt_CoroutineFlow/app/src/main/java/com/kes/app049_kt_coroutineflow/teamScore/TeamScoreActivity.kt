package com.kes.app049_kt_coroutineflow.teamScore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kes.app049_kt_coroutineflow.databinding.ActivityTeamScoreBinding

class TeamScoreActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTeamScoreBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[TeamScoreViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnTeam1.setOnClickListener {
            viewModel.increaseScore(Team.TEAM_1)
        }
        binding.btnTeam2.setOnClickListener {
            viewModel.increaseScore(Team.TEAM_2)
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.tvTeam1Score.text = it.score1.toString()
            binding.tvTeam2Score.text = it.score2.toString()
            when(it) {
                is TeamScoreState.Game -> {}
                is TeamScoreState.Winner -> {
//                    Toast.makeText(
//                        this,
//                        "Winner: ${it.winner}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    binding.apply {
                        btnTeam1.isEnabled = false
                        btnTeam2.isEnabled = false
                        tvWinner.text = "Winner: ${it.winner}"
                        tvWinner.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TeamScoreActivity::class.java)
        }
    }
}