package com.perpushub.bandung.admin.ui.main.home

import com.perpushub.bandung.admin.common.model.Book

data class HomeUiState(
    val books: List<Book> = listOf(),
    val searchQuery: String = "",
    val isAddBookDialogOpen: Boolean = false,
    val isLoading: Boolean = false
)
