package com.perpushub.bandung.admin.ui.main.bookdetail

import com.perpushub.bandung.admin.common.model.BookCopy
import com.perpushub.bandung.admin.common.model.BookDetail
import com.perpushub.bandung.admin.common.model.LibraryDetail

data class BookDetailUiState(
    val book: BookDetail? = null,
    val bookCopies: List<BookCopy> = listOf(),
    val libraries: List<LibraryDetail> = listOf(),
    val isLibraryDialogOpen: Boolean = false,
    val isLibraryDialogLoading: Boolean = false,
    val isLoading: Boolean = false
)
