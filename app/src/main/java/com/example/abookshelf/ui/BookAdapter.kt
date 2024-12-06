package com.example.abookshelf.ui

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.abookshelf.R
import com.example.abookshelf.data.model.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var books: List<Book> = emptyList()

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.bookImage)
        private val titleView: TextView = itemView.findViewById(R.id.bookTitle)
        private val authorView: TextView = itemView.findViewById(R.id.bookAuthor)

        init {
            itemView.setOnClickListener {
                showBookDescription(books[adapterPosition])
            }
        }

        fun bind(book: Book) {
            titleView.text = book.title
            authorView.text = book.authors?.joinToString(", ") ?: "Unknown Author"
            book.imageLinks?.thumbnail?.let { url ->
                // Replace http with https to avoid cleartext traffic error
                val secureUrl = url.replace("http://", "https://")
                imageView.load(secureUrl) {
                    crossfade(true)
                    placeholder(R.drawable.book_placeholder)
                    error(R.drawable.book_placeholder)
                }
            } ?: imageView.setImageResource(R.drawable.book_placeholder)
        }

        private fun showBookDescription(book: Book) {
            val context = itemView.context
            val dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_book_description, null)

            // Initialize dialog views
            val dialogImage = dialogView.findViewById<ImageView>(R.id.dialogBookImage)
            val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogBookTitle)
            val dialogAuthor = dialogView.findViewById<TextView>(R.id.dialogBookAuthor)
            val dialogDescription = dialogView.findViewById<TextView>(R.id.dialogBookDescription)

            // Set dialog content
            dialogTitle.text = book.title
            dialogAuthor.text = book.authors?.joinToString(", ") ?: "Unknown Author"
            dialogDescription.text = book.description ?: "No description available"

            // Load image
            book.imageLinks?.thumbnail?.let { url ->
                val secureUrl = url.replace("http://", "https://")
                dialogImage.load(secureUrl) {
                    crossfade(true)
                    placeholder(R.drawable.book_placeholder)
                    error(R.drawable.book_placeholder)
                }
            } ?: dialogImage.setImageResource(R.drawable.book_placeholder)

            // Show dialog
            AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}
