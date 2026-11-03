package com.example.app_1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SquareAdapter(initialSquareIds: List<Int>) :
    RecyclerView.Adapter<SquareAdapter.SquareViewHolder>() {

    private val squareIds: MutableList<Int> = initialSquareIds.toMutableList()

    // Очередь удалённых: первый удалённый — первый в очереди.
    private val removedSquareIds: ArrayDeque<Int> = ArrayDeque()

    private var nextSquareId: Int = (initialSquareIds.maxOrNull() ?: -1) + 1

    val squareCount: Int
        get() = squareIds.size

    fun snapshotIds(): List<Int> = squareIds.toList()
    fun snapshotRemovedIds(): List<Int> = removedSquareIds.toList()
    fun snapshotNextId(): Int = nextSquareId

    class SquareViewHolder(private val squareView: TextView) :
        RecyclerView.ViewHolder(squareView) {

        fun square_bind(squareId: Int, onClick: (Int) -> Unit) {
            val backgroundColorResource = if (squareId % 2 == 0) {
                R.color.square_even_background
            } else {
                R.color.square_odd_background
            }
            squareView.text = squareId.toString()
            squareView.setBackgroundColor(
                ContextCompat.getColor(
                    squareView.context,
                    backgroundColorResource
                )
            )
            squareView.setOnClickListener { onClick(squareId) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SquareViewHolder {
        val squareView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_square, parent, false) as TextView
        return SquareViewHolder(squareView)
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        val squareId = squareIds[position]
        holder.square_bind(squareId) { clickedId ->
            square_removeById(clickedId)
        }
    }

    override fun getItemCount(): Int = squareIds.size

    fun square_add(): Boolean {
        val restored = removedSquareIds.isNotEmpty()
        val squareId = if (restored) {
            removedSquareIds.removeFirst()   // ← FIFO: первый удалённый вернётся первым
        } else {
            nextSquareId++
        }
        squareIds.add(squareId)
        notifyItemInserted(squareIds.size - 1)
        return restored
    }

    fun square_removeById(squareId: Int) {
        val position = squareIds.indexOf(squareId)
        if (position == -1) return
        squareIds.removeAt(position)
        removedSquareIds.addLast(squareId)   // ← в конец очереди
        notifyItemRemoved(position)
    }

    fun restoreState(
        ids: List<Int>,
        removedIds: List<Int>,
        nextId: Int
    ) {
        squareIds.clear()
        squareIds.addAll(ids)
        removedSquareIds.clear()
        removedSquareIds.addAll(removedIds)
        nextSquareId = nextId
        notifyDataSetChanged()
    }
}