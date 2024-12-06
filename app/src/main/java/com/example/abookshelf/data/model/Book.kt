package com.example.abookshelf.data.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val publishedDate: String?
)

data class ImageLinks(
    val thumbnail: String?,
    val smallThumbnail: String?
)

data class BookResponse(
    val items: List<BookItem>,
    val totalItems: Int
)

data class BookItem(
    val id: String,
    val volumeInfo: Book
)
