package com.example.myapplicationlab10.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplicationlab10.bookshelf.BookshelfApplication
import com.example.myapplicationlab10.bookshelf.data.BooksRepository
import com.example.myapplicationlab10.bookshelf.data.OfflineBooksRepository
import com.example.myapplicationlab10.bookshelf.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookshelfViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookshelfUiState>(BookshelfUiState.Loading)
    val uiState: StateFlow<BookshelfUiState> = _uiState.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        _uiState.value = BookshelfUiState.Loading
        viewModelScope.launch {
            try {
                val books = booksRepository.getBooks()
                _uiState.value = BookshelfUiState.Success(books)
            } catch (e: Exception) {
                // 网络失败自动切换到离线数据
                val offlineBooks = OfflineBooksRepository().getBooks()
                _uiState.value = BookshelfUiState.Success(offlineBooks)
            }
        }
    }

    fun selectBook(book: Book) {
        _selectedBook.value = book
    }

    fun closeDetailDialog() {
        _selectedBook.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BookshelfApplication)
                BookshelfViewModel(application.container.booksRepository)
            }
        }
    }
}