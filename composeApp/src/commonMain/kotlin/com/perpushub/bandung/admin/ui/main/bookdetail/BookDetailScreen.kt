package com.perpushub.bandung.admin.ui.main.bookdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.perpushub.bandung.admin.common.model.BookDetail
import com.perpushub.bandung.admin.ui.main.common.component.BookCover
import com.perpushub.bandung.admin.ui.main.common.component.ExpanderItem
import com.perpushub.bandung.admin.ui.main.common.component.Header
import com.perpushub.bandung.admin.ui.main.common.component.ItemRow
import com.perpushub.bandung.admin.ui.main.common.component.LibraryDialog
import com.perpushub.bandung.admin.ui.main.common.extension.alignHorizontalSpace
import com.perpushub.bandung.admin.ui.main.common.util.DateUtil
import com.perpushub.bandung.admin.ui.theme.AppTheme
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.AccentButton
import io.github.composefluent.component.DialogSize
import io.github.composefluent.component.LocalContentDialog
import io.github.composefluent.component.ProgressRing
import io.github.composefluent.component.ProgressRingSize
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ovh.plrapps.mapcompose.ui.state.MapState

@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        mapState = viewModel.mapState
    )
}

@Composable
fun BookDetailScreenContent(
    uiState: BookDetailUiState,
    onEvent: (BookDetailEvent) -> Unit,
    mapState: MapState? = null
) {
    val dialog = LocalContentDialog.current
    val scope = rememberCoroutineScope()

    if (uiState.isLoading || uiState.book == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressRing()
        }
    } else {
        LibraryDialog(
            title = "Pilih perpustakaan",
            bookCopies = uiState.bookCopies,
            libraries = uiState.libraries,
            visible = uiState.isLibraryDialogOpen,
            onDismissRequest = {
                onEvent(BookDetailEvent.OnLibraryDialogClose)
            },
            mapState = mapState,
            allowUnavailableSelection = true,
            onSelectClick = {
                onEvent(
                    BookDetailEvent.OnAddBookCopy(it.id) {
                        scope.launch {
                            dialog.show(
                                title = "Berhasil",
                                contentText = "Salinan berhasil ditambahkan.",
                                primaryButtonText = "Tutup",
                                size = DialogSize.Min
                            )
                        }
                    }
                )
            },
            loading = uiState.isLibraryDialogLoading
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val scrollState = rememberScrollState()

            Header(
                text = {
                    Column {
                        Text(
                            text = uiState.book.title,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = if (uiState.book.authors.isEmpty()) {
                                "Unknown"
                            } else {
                                uiState.book.authors.joinToString(", ") { it.name }
                            },
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = FluentTheme.typography.body
                        )
                    }
                },
                modifier = Modifier.alignHorizontalSpace(),
                actions = {
                    AccentButton(
                        onClick = {
                            onEvent(BookDetailEvent.OnLibraryDialogOpen)
                        }
                    ) {
                        Text("Tambah salinan")
                    }
                }
            )
            ScrollbarContainer(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier.weight(1f)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .alignHorizontalSpace()
                        .fillMaxHeight()
                        .verticalScroll(scrollState)
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            bottom = 32.dp
                        )
                ) {
                    if (maxWidth >= 736.dp) {
                        Row {
                            BookCoverSection(
                                book = uiState.book
                            )
                            Spacer(Modifier.width(16.dp))
                            BookAboutSection(
                                book = uiState.book
                            )
                        }
                    } else {
                        Column {
                            BookCoverSection(
                                book = uiState.book,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(16.dp))
                            BookAboutSection(
                                book = uiState.book
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookCoverSection(
    book: BookDetail,
    modifier: Modifier = Modifier
) {
    BookCover(
        coverUrl = book.coverUrl,
        contentDescription = book.title,
        width = 240.dp,
        modifier = modifier,
        progressRingSize = ProgressRingSize.Medium
    )
}

@Composable
private fun BookAboutSection(
    book: BookDetail
) {
    ExpanderItem(
        title = "Tentang buku ini",
        defaultExpanded = true
    ) {
        ItemRow(
            items = listOf(
                {
                    Text("Penerbit")
                },
                {
                    Text(book.publisher)
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("Tanggal publikasi")
                },
                {
                    Text(
                        DateUtil.formatDate(
                            book.publishedDate
                        )
                    )
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("ISBN 10")
                },
                {
                    Text(book.isbn10)
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("ISBN 13")
                },
                {
                    Text(book.isbn13)
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("Jumlah halaman")
                },
                {
                    Text(book.pageCount.toString())
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("Kategori")
                },
                {
                    Text(book.categories.joinToString(", ") { it.name })
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text("Bahasa")
                },
                {
                    Text(book.language.uppercase())
                }
            )
        )
        ItemRow(
            items = listOf(
                {
                    Text(book.description)
                }
            ),
            separatorVisible = false
        )
    }
}

@Composable
@Preview(widthDp = 1280, heightDp = 720)
private fun BookDetailScreenPreview() {
    AppTheme {
        BookDetailScreenContent(
            uiState = BookDetailUiState(),
            onEvent = {}
        )
    }
}