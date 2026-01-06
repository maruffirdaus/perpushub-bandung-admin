package com.perpushub.bandung.admin.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import com.perpushub.bandung.admin.ui.navigation.main.MainNavKey
import com.perpushub.bandung.admin.ui.theme.AppTheme
import io.github.composefluent.component.Icon
import io.github.composefluent.component.MenuItem
import io.github.composefluent.component.NavigationDefaults
import io.github.composefluent.component.NavigationDisplayMode
import io.github.composefluent.component.NavigationView
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberNavigationState
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Book
import io.github.composefluent.icons.regular.Home

@Composable
fun MainScreen(
    navDisplay: @Composable () -> Unit,
    backStack: List<NavKey>,
    onNavigate: (NavKey) -> Unit,
    onNavigateBack: () -> Unit,
    backButtonEnabled: Boolean = true
) {
    MainScreenContent(
        navDisplay = navDisplay,
        backStack = backStack,
        onNavigate = onNavigate,
        onNavigateBack = onNavigateBack,
        backButtonEnabled = backButtonEnabled
    )
}

@Composable
fun MainScreenContent(
    navDisplay: @Composable () -> Unit,
    backStack: List<NavKey>,
    onNavigate: (NavKey) -> Unit,
    onNavigateBack: () -> Unit,
    backButtonEnabled: Boolean = false
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isAtLeastMediumBreakpoint =
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    val isAtLeastExpandedBreakpoint =
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

    NavigationView(
        menuItems = {
            item {
                val isSelected = backStack.lastOrNull() is MainNavKey.Home

                MenuItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) onNavigate(MainNavKey.Home)
                    },
                    text = {
                        Text("Beranda")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Regular.Home,
                            contentDescription = "Beranda"
                        )
                    }
                )
            }
            item {
                val isSelected = backStack.lastOrNull() is MainNavKey.Borrowing

                MenuItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) onNavigate(MainNavKey.Borrowing)
                    },
                    text = {
                        Text("Peminjaman")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Regular.Book,
                            contentDescription = "Peminjaman"
                        )
                    }
                )
            }
        },
        modifier = Modifier.safeContentPadding(),
        displayMode = if (isAtLeastExpandedBreakpoint) {
            NavigationDisplayMode.Left
        } else if (isAtLeastMediumBreakpoint) {
            NavigationDisplayMode.LeftCompact
        } else {
            NavigationDisplayMode.LeftCollapsed
        },
        state = rememberNavigationState(
            initialExpanded = false
        ),
        backButton = if (backButtonEnabled) {
            {
                NavigationDefaults.BackButton(
                    onClick = onNavigateBack,
                    disabled = backStack.size == 1
                )
            }
        } else {
            {}
        },
        contentPadding = if (isAtLeastMediumBreakpoint) {
            PaddingValues()
        } else {
            PaddingValues(top = 48.dp)
        },
    ) {
        navDisplay()
    }
}

@Composable
@Preview(widthDp = 1280, heightDp = 720)
private fun MainScreenPreview() {
    AppTheme {
        MainScreenContent(
            navDisplay = {},
            backStack = emptyList(),
            onNavigate = {},
            onNavigateBack = {}
        )
    }
}