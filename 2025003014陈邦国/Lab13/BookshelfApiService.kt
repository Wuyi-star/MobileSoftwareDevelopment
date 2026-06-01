package com.example.myapplicationlab10.bookshelf.network

import com.example.myapplicationlab10.bookshelf.model.BookDto
import retrofit2.http.GET

interface BookshelfApiService {
    @GET("photos")
    suspend fun getBooks(): List<BookDto>
}