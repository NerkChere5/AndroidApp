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

        val savedSquareCount = savedInstanceState
            ?.getInt(getString(R.string.state_key_square_count))
            ?: 0

        squareAdapter = SquareAdapter(savedSquareCount)

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
        outState.putInt(
            getString(R.string.state_key_square_count),
            squareAdapter._squareCount
        )
    }

    private fun squareAddButton_on_click() {
        squareAdapter.square_add()
        squareRecyclerView.smoothScrollToPosition(squareAdapter._squareCount - 1)
    }
}