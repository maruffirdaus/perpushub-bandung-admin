package com.perpushub.bandung.admin.di

import com.perpushub.bandung.admin.ui.main.bookdetail.BookDetailViewModel
import com.perpushub.bandung.admin.ui.main.borrowing.BorrowingViewModel
import com.perpushub.bandung.admin.ui.main.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (id: Int) -> BookDetailViewModel(id, get(), get(), get(), get()) }
    viewModel { BorrowingViewModel(get(), get(), get()) }
}