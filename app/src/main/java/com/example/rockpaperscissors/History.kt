package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {

    private val rounds = arrayListOf<Round>()
    private val roundAdapter = RoundAdapter(rounds)
    private lateinit var roundRepository: RoundRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        actionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = "Your Game History"
        roundRepository = RoundRepository(this)

        initViews()
    }


    private fun initViews(){
        rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.adapter = roundAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getRoundsFromDatabase()
    }

    private fun getRoundsFromDatabase(){
        mainScope.launch {
            val rounds = withContext(Dispatchers.IO){
                roundRepository.getAllRounds()
            }
            this@History.rounds.clear()
            this@History.rounds.addAll(rounds)
            this@History.roundAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_delete_history -> {
                deleteHistory()
                true
            }
                else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteHistory(){
        mainScope.launch {
            withContext(Dispatchers.IO) {
                roundRepository.deleteAllRounds()
            }
            getRoundsFromDatabase()
        }
    }
}
