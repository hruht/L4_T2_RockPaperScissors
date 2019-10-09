package com.example.rockpaperscissors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_round.view.*

class RoundAdapter(private val rounds: List<Round>) : RecyclerView.Adapter<RoundAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_round, parent, false)
        )
    }

    override fun getItemCount(): Int = rounds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rounds[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(round: Round) {
            if (round.result == 0){
                itemView.tvItemResult.text = "Computer wins!"
            } else if (round.result == 1){
                itemView.tvItemResult.text = "Draw"
            } else if (round.result == 2){
                itemView.tvItemResult.text = "You win!"
            } else {
                itemView.tvItemResult.text = "I think someone cheated"
            }

            itemView.tvItemTime.text = round.date
            itemView.tvItemVS.text = "V.S."

            if (round.compchoice == "rock"){
                itemView.ivItemComChoice.setImageResource(R.drawable.rock)
            } else if (round.compchoice == "paper"){
                itemView.ivItemComChoice.setImageResource(R.drawable.paper)
            } else {
                itemView.ivItemComChoice.setImageResource(R.drawable.scissors)
            }

            if (round.yourchoice == "rock"){
                itemView.ivItemYourChoice.setImageResource(R.drawable.rock)
            } else if (round.yourchoice == "paper"){
                itemView.ivItemYourChoice.setImageResource(R.drawable.paper)
            } else {
                itemView.ivItemYourChoice.setImageResource(R.drawable.scissors)
            }
        }
    }
}