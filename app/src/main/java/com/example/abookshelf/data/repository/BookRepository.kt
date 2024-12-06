package com.example.abookshelf.data.repository

import com.example.abookshelf.data.api.BookService
import com.example.abookshelf.data.model.Book
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val bookService = retrofit.create(BookService::class.java)

    suspend fun searchBooks(query: String): List<Book> {
        return try {
            val response = bookService.searchBooks(query)
            response.items.map { it.volumeInfo }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
