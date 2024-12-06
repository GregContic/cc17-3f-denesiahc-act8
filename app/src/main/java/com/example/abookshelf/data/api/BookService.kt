package com.example.abookshelf.data.api

import com.example.abookshelf.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40
    ): BookResponse
}
