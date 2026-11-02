package com.example.app_1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SquareAdapter(initialSquareCount: Int) :
    RecyclerView.Adapter<SquareAdapter.SquareViewHolder>() {

    var _squareCount: Int = initialSquareCount
        private set

    class SquareViewHolder(private val squareView: TextView) :
        RecyclerView.ViewHolder(squareView) {

        fun square_bind(position: Int) {
            val backgroundColorResource = if (position % 2 == 0) {
                R.color.square_even_background
            } else {
                R.color.square_odd_background
            }
            squareView.text = position.toString()
            squareView.setBackgroundColor(
                ContextCompat.getColor(
                    squareView.context,
                    backgroundColorResource
                )
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SquareViewHolder {
        val squareView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_square,
                parent,
                false
            ) as TextView
        return SquareViewHolder(squareView)
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        holder.square_bind(position)
    }

    override fun getItemCount(): Int = _squareCount

    fun square_add() {
        _squareCount++
        notifyItemInserted(_squareCount - 1)
    }
}