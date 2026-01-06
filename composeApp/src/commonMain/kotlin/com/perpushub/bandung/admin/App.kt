package com.perpushub.bandung.admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.perpushub.bandung.admin.di.networkModule
import com.perpushub.bandung.admin.di.repositoryModule
import com.perpushub.bandung.admin.di.uiModule
import com.perpushub.bandung.admin.di.viewModelModule
import com.perpushub.bandung.admin.ui.common.messaging.MessageHost
import com.perpushub.bandung.admin.ui.navigation.AppNavDisplay
import com.perpushub.bandung.admin.ui.navigation.AppNavKey
import com.perpushub.bandung.admin.ui.navigation.main.MainNavKey
import com.perpushub.bandung.admin.ui.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinConfiguration

@Composable
@Preview
fun App(
    backButtonEnabled: Boolean = true,
    appBackStack: MutableList<NavKey> = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = AppNavKey.serializersModule
        },
        AppNavKey.Main
    ),
    mainBackStack: MutableList<NavKey> = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = MainNavKey.serializersModule
        },
        MainNavKey.Home
    )
) {
    KoinApplication(
        configuration = KoinConfiguration {
            modules(networkModule, repositoryModule, uiModule, viewModelModule)
        }
    ) {
        AppTheme {
            MessageHost()
            AppNavDisplay(
                appBackStack = appBackStack,
                mainBackStack = mainBackStack,
                backButtonEnabled = backButtonEnabled
            )
        }
    }
}