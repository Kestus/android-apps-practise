package com.kes.app049_kt_coroutineflow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.kes.app049_kt_coroutineflow.currencyActivity.CurrencyActivity
import com.kes.app049_kt_coroutineflow.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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

        binding.btnUsers.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
    }

    private fun test() {
        lifecycleScope.launch {
            getFlowWithFlowBuilder()
                .filter { it % 2 == 0 }
                .map { "Number: $it" }
                .collect {
                    Log.d(TAG, it)
                }
        }
    }

    private fun getFlowByFlowOfBuilder(): Flow<Int> {
        return flowOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61)
    }

    private fun getFlowWithFlowBuilder(): Flow<Int> {
        val numbers = intArrayOf(3, 4, 8, 16, 5, 7, 11, 32, 41, 28, 43, 47, 84, 116, 53, 59, 61)
        return flow {
            for (num in numbers) {
                emit(num)
                Log.d(TAG, "Emitted: $num")
            }
        }
    }

    companion object {
        private const val TAG = "TAG_MainActivity"
    }
}