package com.perpushub.bandung.admin.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpushub.bandung.admin.data.repository.BookRepository
import com.perpushub.bandung.admin.ui.common.messaging.UiError
import com.perpushub.bandung.admin.ui.common.messaging.UiMessageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookRepository: BookRepository,
    private val uiMessageManager: UiMessageManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
        .onStart {
            loadInitialData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeUiState()
        )

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                _uiState.update {
                    it.copy(books = bookRepository.getBooks(uiState.value.searchQuery))
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchQueryChange -> changeSearchQuery(event.query)
            is HomeEvent.OnBooksSearch -> searchBooks()
            is HomeEvent.OnAddBookDialogOpen -> openAddBookDialog()
            is HomeEvent.OnAddBookDialogClose -> closeAddBookDialog()
            is HomeEvent.OnBookAdd -> addBook(event.isbn, event.onSuccess)
        }
    }

    private fun changeSearchQuery(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    private fun searchBooks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                uiState.value.searchQuery.let { query ->
                    if (query.isNotBlank()) {
                        _uiState.update {
                            it.copy(books = bookRepository.getBooks(uiState.value.searchQuery.trim()))
                        }
                    }
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun openAddBookDialog() {
        _uiState.update {
            it.copy(isAddBookDialogOpen = true)
        }
    }

    private fun closeAddBookDialog() {
        _uiState.update {
            it.copy(isAddBookDialogOpen = false)
        }
    }

    private fun addBook(isbn: String, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                val id = bookRepository.addBook(isbn)
                _uiState.update {
                    it.copy(books = bookRepository.getBooks(uiState.value.searchQuery))
                }
                onSuccess(id)
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}