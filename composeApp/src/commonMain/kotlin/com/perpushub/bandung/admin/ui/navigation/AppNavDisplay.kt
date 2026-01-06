package com.perpushub.bandung.admin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.perpushub.bandung.admin.ui.main.MainScreen
import com.perpushub.bandung.admin.ui.navigation.main.MainNavDisplay
import com.perpushub.bandung.admin.ui.navigation.main.MainNavKey

@Composable
fun AppNavDisplay(
    appBackStack: MutableList<NavKey>,
    mainBackStack: MutableList<NavKey>,
    backButtonEnabled: Boolean = true
) {
    NavDisplay(
        backStack = appBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppNavKey.Main> {
                MainScreen(
                    navDisplay = {
                        MainNavDisplay(
                            backStack = mainBackStack
                        )
                    },
                    backStack = mainBackStack,
                    onNavigate = { key ->
                        when (key) {
                            is AppNavKey -> appBackStack[0] = key
                            is MainNavKey -> mainBackStack.add(key)
                        }
                    },
                    onNavigateBack = {
                        if (mainBackStack.size > 1) {
                            mainBackStack.removeLast()
                        }
                    },
                    backButtonEnabled = backButtonEnabled
                )
            }
        }
    )
}