package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGameBinding
    private var gamemodel: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.startbtn.setOnClickListener(this)
        binding.startbtn.visibility = View.VISIBLE


        Gamedata.gameModel.observe(this) {

          gamemodel = it
            setUi()
        }
    }

     fun setUi() {
         gamemodel?.apply{
             binding.btn0.text=filedpos[0]
             binding.btn1.text=filedpos[1]
             binding.btn2.text=filedpos[2]
             binding.btn3.text=filedpos[3]
             binding.btn4.text=filedpos[4]
             binding.btn5.text=filedpos[5]
             binding.btn6.text=filedpos[6]
             binding.btn7.text=filedpos[7]
             binding.btn8.text=filedpos[8]

             binding.gamestatic.text=
                 when(gameStaus){
                     Gamestatus.CREATED->{
                         "gameID:" +gameid
                     }
                     Gamestatus.JOINTED -> {
                         "CLICK TO START"
                     }
                     Gamestatus.INPROGRESS -> {
                         binding.startbtn.visibility = View.INVISIBLE
                         currentplayer + " :Turn"
                     }
                     Gamestatus.FINISHED -> {
                         binding.startbtn.visibility = View.VISIBLE
                         if(winner.isNotEmpty() &&  winner != "Draw") " $winner IS WINNER"
                         else "Draw"
                     }
                     else -> ""

                 }
             binding.startbtn.setOnClickListener {
                 startgame()
             }


         }

     }
    override fun onClick(v: View?) {
   gamemodel?.apply{
       if(gameStaus!=Gamestatus.INPROGRESS) {
           Toast.makeText(this@GameActivity, "GAME NOT STARTED", Toast.LENGTH_SHORT).show()
           return
       }
       val clickedbtn = (v?.tag as String).toInt()
       if(filedpos[clickedbtn].isEmpty()){
         filedpos[clickedbtn]=currentplayer
           currentplayer=if (currentplayer=="X") "O" else "X"
           checkforwinner()
           updategamedata(this)
       }

   }

    }

    fun startgame() {
      gamemodel?.apply {
          updategamedata(
              GameModel(
                  gameid=gameid,

                  gameStaus = Gamestatus.INPROGRESS,

              )
          )
      }
    }
    fun updategamedata(model :GameModel){
        Gamedata.saveGameModel(model)
    }
    fun checkforwinner(){
        val winngetPos = arrayOf(
            arrayOf(0,1,2) ,
            arrayOf(3,4,5),
            arrayOf(6,7,8),
            arrayOf(0,3,6),
            arrayOf(1,4,7),
            arrayOf(2,5,8),
            arrayOf(0,4,8),
            arrayOf(2,4,6)

        )

        gamemodel?.apply {
            for (i in winngetPos){
                if(filedpos[i[0]]==filedpos[i[1]] && filedpos[i[1]]==filedpos[i[2]]&&filedpos[i[0]].isNotEmpty()){
                    gameStaus= Gamestatus.FINISHED
                    winner=filedpos[i[0]]
                    updategamedata(this)
                    return
                }
            }
            if(filedpos.none{it.isEmpty()}){
                gameStaus=Gamestatus.FINISHED
                winner="DRAW"
            }


            updategamedata(this)
        }
    }
}
