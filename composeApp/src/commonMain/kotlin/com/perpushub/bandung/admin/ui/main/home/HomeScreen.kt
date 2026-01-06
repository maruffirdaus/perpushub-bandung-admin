package com.perpushub.bandung.admin.ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import com.perpushub.bandung.admin.ui.main.common.component.ActionDivider
import com.perpushub.bandung.admin.ui.main.common.component.Header
import com.perpushub.bandung.admin.ui.main.common.extension.alignHorizontalSpace
import com.perpushub.bandung.admin.ui.main.home.component.AddBookDialog
import com.perpushub.bandung.admin.ui.main.home.component.HorizontalBookItem
import com.perpushub.bandung.admin.ui.navigation.main.MainNavKey
import com.perpushub.bandung.admin.ui.theme.AppTheme
import io.github.composefluent.component.AccentButton
import io.github.composefluent.component.Icon
import io.github.composefluent.component.ProgressRing
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.TextBoxButton
import io.github.composefluent.component.TextField
import io.github.composefluent.component.rememberScrollbarAdapter
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Add
import io.github.composefluent.icons.regular.Search
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onNavigate: (NavKey) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    onNavigate: (NavKey) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isAtLeastMediumBreakpoint =
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    val isAtLeastExpandedBreakpoint =
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
    val scope = rememberCoroutineScope()

    AddBookDialog(
        visible = uiState.isAddBookDialogOpen,
        onAddClick = { isbn ->
            onEvent(
                HomeEvent.OnBookAdd(isbn) { id ->
                    onNavigate(MainNavKey.BookDetail(id))
                }
            )
        },
        onDismissRequest = {
            onEvent(HomeEvent.OnAddBookDialogClose)
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val lazyListState = rememberLazyListState()

        Header(
            text = "Beranda",
            modifier = Modifier.alignHorizontalSpace(),
            actionsOnly = !isAtLeastMediumBreakpoint,
            actions = {
                TextField(
                    value = uiState.searchQuery,
                    onValueChange = {
                        onEvent(
                            HomeEvent.OnSearchQueryChange(
                                it
                            )
                        )
                    },
                    modifier = Modifier.let {
                        if (isAtLeastMediumBreakpoint) {
                            it.width(if (isAtLeastExpandedBreakpoint) 320.dp else 240.dp)
                        } else {
                            it.weight(1f)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onEvent(HomeEvent.OnBooksSearch)
                            scope.launch {
                                lazyListState.animateScrollToItem(0)
                            }
                        }
                    ),
                    trailing = {
                        TextBoxButton(
                            onClick = {
                                onEvent(HomeEvent.OnBooksSearch)
                                scope.launch {
                                    lazyListState.animateScrollToItem(0)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Regular.Search,
                                contentDescription = "Cari"
                            )
                        }
                    },
                    placeholder = {
                        Text("Cari")
                    }
                )
                ActionDivider()
                AccentButton(
                    onClick = {
                        onEvent(HomeEvent.OnAddBookDialogOpen)
                    },
                    iconOnly = !isAtLeastMediumBreakpoint
                ) {
                    if (isAtLeastMediumBreakpoint) {
                        Text("Tambah buku")
                    } else {
                        Icon(
                            imageVector = Icons.Regular.Add,
                            contentDescription = "Tambah buku"
                        )
                    }
                }
            }
        )
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ProgressRing()
            }
        } else {
            ScrollbarContainer(
                adapter = rememberScrollbarAdapter(lazyListState),
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .alignHorizontalSpace()
                        .fillMaxHeight(),
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        start = 32.dp,
                        end = 32.dp,
                        bottom = 32.dp
                    )
                ) {
                    if (uiState.books.isEmpty()) {
                        item {
                            Text(
                                text = if (uiState.searchQuery.isNotBlank()) {
                                    "Buku dengan kata kunci \"${uiState.searchQuery}\" tidak ditemukan."
                                } else {
                                    "Belum ada buku."
                                }
                            )
                        }
                    } else {
                        itemsIndexed(uiState.books) { index, book ->
                            HorizontalBookItem(
                                book = book,
                                onClick = {
                                    onNavigate(MainNavKey.BookDetail(book.id))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                alternate = index % 2 == 0
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(widthDp = 1280, heightDp = 720)
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            onEvent = {},
            onNavigate = {}
        )
    }
}