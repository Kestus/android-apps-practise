package com.kes.app030_kt_cardview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kes.app030_kt_cardview.models.Game

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = GameAdapter(getGames())
        recyclerView.adapter = adapter




    }

    private fun getGames(): ArrayList<Game> {
        val gameList: ArrayList<Game> = ArrayList()
        gameList.add(Game(
            "Warthunder",
            R.drawable.warthunder
        ))
        gameList.add(Game(
            "Destiny 2",
            R.drawable.destiny2
        ))
        gameList.add(Game(
            "Black Desert Online",
            R.drawable.bdo
        ))
        gameList.add(Game(
            "Cuphead",
            R.drawable.cuphead
        ))
        gameList.add(Game(
            "Stellaris",
            R.drawable.stellaris
        ))
        gameList.add(Game(
            "Terraria",
            R.drawable.terraria
        ))
        gameList.add(Game(
            "Slay the Spire",
            R.drawable.slaythespire
        ))

        return gameList
    }
}