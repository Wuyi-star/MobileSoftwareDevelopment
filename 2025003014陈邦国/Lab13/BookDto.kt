package com.example.myapplicationlab10.bookshelf.model

import com.google.gson.annotations.SerializedName

data class BookDto(
    val id: String = "",
    @SerializedName("img_src")
    val imgSrc: String = "",
)

fun BookDto.asDomainModel(): Book {
    return Book(
        id = this.id,
        coverUrl = this.imgSrc,
        title = "Book #${this.id}"
    )
}