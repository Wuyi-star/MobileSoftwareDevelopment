package com.example.myapplicationlab10.bookshelf

import com.example.myapplicationlab10.bookshelf.data.BooksRepository
import com.example.myapplicationlab10.bookshelf.data.NetworkBooksRepository
import com.example.myapplicationlab10.bookshelf.network.BookshelfApiService
import com.example.myapplicationlab10.bookshelf.network.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}