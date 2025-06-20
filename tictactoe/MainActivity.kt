package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playbtn.setOnClickListener {
            creategame()
        }
    }

    private fun creategame() {

        Gamedata.saveGameModel(
            GameModel(

                gameStaus = Gamestatus.JOINTED,

        )
        )
        startgame()
    }

    private fun startgame() {
        startActivity(Intent(this, GameActivity::class.java))
    }
}
