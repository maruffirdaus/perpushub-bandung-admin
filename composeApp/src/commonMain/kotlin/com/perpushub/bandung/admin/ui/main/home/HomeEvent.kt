package com.perpushub.bandung.admin.ui.main.home

sealed class HomeEvent {
    class OnSearchQueryChange(val query: String) : HomeEvent()
    object OnBooksSearch : HomeEvent()
    object OnAddBookDialogOpen : HomeEvent()
    object OnAddBookDialogClose : HomeEvent()
    class OnBookAdd(val isbn: String, val onSuccess: (Int) -> Unit) : HomeEvent()
}