package com.perpushub.bandung.admin.di

import com.perpushub.bandung.admin.ui.common.messaging.UiMessageManager
import org.koin.dsl.module

val uiModule = module {
    single { UiMessageManager() }
}