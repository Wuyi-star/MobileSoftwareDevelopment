package com.example.sports.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sports.R
import com.example.sports.data.LocalSportsDataProvider
import com.example.sports.model.Sport
import com.example.sports.ui.theme.SportsTheme
import com.example.sports.utils.SportsContentType
import android.app.Activity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsApp(
    viewModel: SportsViewModel = viewModel()
) {
    // 为了避免 WindowSizeClass 相关错误，这里直接写死 Compact 模式，保证能运行
    val contentType = SportsContentType.ListOnly

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            SportsAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackButtonClick = { viewModel.navigateToListPage() }
            )
        }
    ) { innerPadding ->
        if (contentType == SportsContentType.ListOnly) {
            if (uiState.isShowingListPage) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(LocalSportsDataProvider.allSports) { sport ->
                        Card(
                            onClick = {
                                viewModel.updateCurrentSport(sport)
                                viewModel.navigateToDetailPage()
                            },
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(sport.title),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(uiState.currentSport.title), style = MaterialTheme.typography.headlineMedium)
                    Text(stringResource(uiState.currentSport.detail), textAlign = TextAlign.Justify)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsAppBar(
    isShowingListPage: Boolean,
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Sports") },
        navigationIcon = {
            if (!isShowingListPage) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewApp() {
    SportsTheme {
        SportsApp()
    }
}