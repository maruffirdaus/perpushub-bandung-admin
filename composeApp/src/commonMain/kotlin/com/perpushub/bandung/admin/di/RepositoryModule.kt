package com.perpushub.bandung.admin.di

import com.perpushub.bandung.admin.data.repository.BookRepository
import com.perpushub.bandung.admin.data.repository.LibraryRepository
import com.perpushub.bandung.admin.data.repository.LoanRepository
import com.perpushub.bandung.admin.data.repository.LoanRequestRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { BookRepository(get()) }
    single { LibraryRepository(get()) }
    single { LoanRequestRepository(get()) }
    single { LoanRepository(get()) }
}