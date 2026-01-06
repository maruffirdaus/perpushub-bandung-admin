package com.perpushub.bandung.admin.di

import com.perpushub.bandung.admin.data.remote.BookService
import com.perpushub.bandung.admin.data.remote.LibraryService
import com.perpushub.bandung.admin.data.remote.LoanRequestService
import com.perpushub.bandung.admin.data.remote.LoanService
import com.perpushub.bandung.admin.data.remote.MapTilerTileStreamProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ovh.plrapps.mapcompose.core.TileStreamProvider

val networkModule = module {
    single { provideHttpClient() }
    single { BookService(get()) }
    single { LibraryService(get()) }
    single { LoanRequestService(get()) }
    single { LoanService(get()) }
    single<TileStreamProvider> { MapTilerTileStreamProvider(get()) }
}

private fun provideHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}