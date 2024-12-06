package com.example.abookshelf

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abookshelf.ui.BookAdapter
import com.example.abookshelf.ui.BookViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: BookViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var searchEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupViews()
        setupRecyclerView()
        setupObservers()
        setupSearch()

        // Initial search
        viewModel.searchBooks("android programming")
    }

    private fun setupViews() {
        searchEditText = findViewById(R.id.searchEditText)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.booksRecyclerView)
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.books.observe(this) { books ->
            adapter.submitList(books)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearch() {
        searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val query = searchEditText.text.toString().trim()
        if (query.isNotEmpty()) {
            viewModel.searchBooks(query)
        }
    }
}