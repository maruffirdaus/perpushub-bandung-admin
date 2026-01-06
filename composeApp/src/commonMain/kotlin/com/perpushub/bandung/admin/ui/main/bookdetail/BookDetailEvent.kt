package com.perpushub.bandung.admin.ui.main.bookdetail

sealed class BookDetailEvent {
    object OnLibraryDialogOpen : BookDetailEvent()
    object OnLibraryDialogClose : BookDetailEvent()
    class OnAddBookCopy(val libraryId: Int, val onSuccess: () -> Unit) : BookDetailEvent()
}