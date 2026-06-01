package com.example.myapplicationlab10.bookshelf.data

import com.example.myapplicationlab10.bookshelf.model.Book

interface BooksRepository {
    suspend fun getBooks(): List<Book>
    suspend fun getBook(id: String): Book
}