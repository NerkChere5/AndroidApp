package com.example.app_1

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    private lateinit var squareAdapter: SquareAdapter
    private lateinit var squareRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keyIds = getString(R.string.state_key_square_ids)
        val keyRemoved = getString(R.string.state_key_removed_ids)
        val keyNext = getString(R.string.state_key_next_id)

        val savedIds = savedInstanceState?.getIntegerArrayList(keyIds)
        val savedRemoved = savedInstanceState?.getIntegerArrayList(keyRemoved)
        val savedNext = savedInstanceState?.getInt(keyNext, 0) ?: 0

        squareAdapter = if (savedIds != null && savedRemoved != null) {
            SquareAdapter(savedIds).also {
                it.restoreState(savedIds, savedRemoved, savedNext)
            }
        } else {
            SquareAdapter(emptyList())
        }

        squareRecyclerView = findViewById(R.id.square_recycler_view)
        squareRecyclerView.layoutManager = GridLayoutManager(
            this,
            resources.getInteger(R.integer.square_column_count)
        )
        squareRecyclerView.adapter = squareAdapter

        findViewById<Button>(R.id.square_add_button).setOnClickListener {
            squareAddButton_on_click()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(
            getString(R.string.state_key_square_ids),
            ArrayList(squareAdapter.snapshotIds())
        )
        outState.putIntegerArrayList(
            getString(R.string.state_key_removed_ids),
            ArrayList(squareAdapter.snapshotRemovedIds())
        )
        outState.putInt(
            getString(R.string.state_key_next_id),
            squareAdapter.snapshotNextId()
        )
    }

    private fun squareAddButton_on_click() {
        squareAdapter.square_add()
        squareRecyclerView.smoothScrollToPosition(squareAdapter.squareCount - 1)
    }
}