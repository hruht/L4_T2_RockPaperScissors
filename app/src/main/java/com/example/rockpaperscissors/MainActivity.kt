package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


const val ADD_H_REQUEST_CODE = 100
const val EXTRA_ROUND = "EXTRA_ROUND"

class MainActivity : AppCompatActivity() {

    private lateinit var roundRepository: RoundRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var wins: Int = 0
    private var draws: Int = 0
    private var losses: Int = 0
    private var random: Int = 0
    private var status: Int = 0
    private var compChoice: String = ""
    private var userChoice: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roundRepository = RoundRepository(this)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        updateStats()    }

    private fun initViews(){

        updateStats()
        val iv_Rock = findViewById(R.id.ivRock) as ImageView
        val iv_Paper = findViewById(R.id.ivPaper) as ImageView
        val iv_Scissors = findViewById(R.id.ivScissors) as ImageView

        iv_Rock.setOnClickListener {
            startGame("rock")
        }

        iv_Paper.setOnClickListener {
            startGame("paper")
        }

        iv_Scissors.setOnClickListener {
            startGame("scissors")
        }
    }

    private fun startGame(choice: String){
        // set current user choice
        userChoice = choice

        // set current computer choice
        compChoice()

        // see who won, set that to status
        status = whoWon()

        // add round information to the database
        addHistory()

        // update current view
        updateView()
    }

    // The computer chooses their move and
    // the choice is set to variable
    // compChoice
    private fun compChoice(){
        random = Random.nextInt(1, 4)

        if (random == 1){
            compChoice = "rock"
        } else if (random == 2){
            compChoice = "paper"
        } else {
            compChoice = "scissors"
        }
    }

    // 0 = You lose! 1 = Draw 2 = You win!
    private fun whoWon(): Int{

        // User chooses rock, computer wins with paper and loses with scissors
        if (userChoice == "rock"){
            if (compChoice == "paper"){
                status = 0
            } else if (compChoice == "scissors") {
                status = 2
            } else {
                status = 1
            }
        }

        // User chooses paper, computer wins with scissors and loses with rock
        if (userChoice == "paper"){
            if (compChoice == "scissors"){
                status = 0
            } else if (compChoice == "rock") {
                status = 2
            } else {
                status = 1
            }
        }

        // User chooses scissors, computer wins with rock and loses with paper
        if (userChoice == "scissors"){
            if (compChoice == "rock"){
                status = 0
            } else if (compChoice == "paper") {
                status = 2
            } else {
                status = 1
            }
        }
        return status
    }

    // Add status, computer choice, user choice
    // and date information to the database
    private fun addHistory(){
        mainScope.launch{

            // For prettier date:
//            val sdf = SimpleDateFormat("dd.M.yyyy HH:mm:ss")
//            val currentDate = sdf.format(Date())

            // Using the example date format now...
            val cal = Calendar.getInstance()
            val currentDate2 = cal.getTime()

            val round = Round(
                result = status,
                date = currentDate2.toString(),
                compchoice = compChoice,
                yourchoice = userChoice
            )
            withContext(Dispatchers.IO) {
                roundRepository.insertRound(round)
            }
        }
    }


    private fun updateView(){

        // Update image views
        if (compChoice == "rock"){
            ivComChoice.setImageDrawable(getDrawable(R.drawable.rock))
        } else if (compChoice == "paper"){
            ivComChoice.setImageDrawable(getDrawable(R.drawable.paper))
        } else {
            ivComChoice.setImageDrawable(getDrawable(R.drawable.scissors))
        }

        if (userChoice == "rock"){
            ivPlayerChoice.setImageDrawable(getDrawable(R.drawable.rock))
        } else if (userChoice == "paper"){
            ivPlayerChoice.setImageDrawable(getDrawable(R.drawable.paper))
        } else {
            ivPlayerChoice.setImageDrawable(getDrawable(R.drawable.scissors))
        }

        // Update status text
        if (status == 0){
            tvStatus.text = "Computer wins!"
        } else if (status == 1){
            tvStatus.text = "Draw"
        } else if (status == 2){
            tvStatus.text = "You Win!"
        } else {
            tvStatus.text = "Hmm..."
        }

        Handler().postDelayed({
            updateStats()
        }, 100)

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_view_history -> {
                viewHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun viewHistory(){
        val intent = Intent(this, History::class.java)
        startActivityForResult(intent, ADD_H_REQUEST_CODE)
    }

    private fun updateStats(){
        mainScope.launch {
            wins = roundRepository.getWins()
            draws = roundRepository.getDraws()
            losses = roundRepository.getLosses()
            tvStat2.text = getString(R.string.tStat2, wins, draws, losses)
        }

    }
}
