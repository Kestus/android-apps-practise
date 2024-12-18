package com.kes.app049_kt_coroutineflow.teamScore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kes.app049_kt_coroutineflow.databinding.ActivityTeamScoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state
                    .collect {
                        Log.d("TAG", "collect")
                        when (it) {
                            is TeamScoreState.Game -> {
                                binding.tvTeam1Score.text = it.score1.toString()
                                binding.tvTeam2Score.text = it.score2.toString()
                            }

                            is TeamScoreState.Winner -> {
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
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TeamScoreActivity::class.java)
        }
    }
}