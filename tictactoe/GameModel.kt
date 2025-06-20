package com.example.tictactoe

import kotlin.random.Random
import kotlin.random.nextInt

data class GameModel(
    var gameid :String="-1",
    var filedpos:MutableList<String> = mutableListOf("","","","","","","","",""),
    var winner:String=" ",
    var gameStaus: Gamestatus=Gamestatus.CREATED,
    var currentplayer:String=(arrayOf("X","O")[Random.nextInt(2)])
)

 enum class Gamestatus {
     CREATED,
     JOINTED,
     INPROGRESS,
     FINISHED
 }
