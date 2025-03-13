package com.route.newsappc41gmonthu.news

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.route.newsappc41gmonthu.NewsScreenContent
import com.route.newsappc41gmonthu.R
import com.route.newsappc41gmonthu.ui.theme.gray

import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.SourcesItemEntity

@Composable
fun NewsScreen(
    categoryAPIKey: String,
    viewModel: NewsViewModel = hiltViewModel(), // Jetpack Compose
    modifier: Modifier = Modifier,
    // Clean Arch - Kotlin Coroutines (Kotlin Flows) - Dependency Injection // Practice
    //   Watch session  (3 H) ->
    //       E-Commerce
) {
    val sourcesList = viewModel.sourcesList
    val articlesList = viewModel.articlesList

    LaunchedEffect(Unit) {
        viewModel.getSources(categoryAPIKey)
    }
    LaunchedEffect(viewModel.selectedSourceId.value) {
        viewModel.getNewsBySource()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (sourcesList.isNotEmpty())
            SourcesTabsLazyRow(sources = sourcesList) { sourceId ->
                articlesList.clear()
                viewModel.selectedSourceId.value = sourceId
            }
        NewsList(articlesList, modifier = Modifier.fillMaxSize())
    }
    if (viewModel.isLoading.value)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    if (viewModel.errorState.value.isNotEmpty()) {
        ErrorDialog(viewModel = viewModel)
    }
}

@Composable
fun ErrorDialog(modifier: Modifier = Modifier, viewModel: NewsViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.errorState.value = "" },
        confirmButton = {
            TextButton(onClick = { viewModel.errorState.value = "" }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        title = {
            Text(text = viewModel.errorState.value, color = Color.Black, fontSize = 14.sp)
        })
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(viewModel = viewModel())
}

@Composable
fun SourcesTabsLazyRow(
    sources: List<SourcesItemEntity>,
    modifier: Modifier = Modifier,
    onTabSelected: (sourceId: String) -> Unit,
) {
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedModifier = Modifier.drawBehind {
        val width = size.width
        val height = size.height
        drawLine(
            color = Color.White,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = 2.dp.toPx(),
        )
    }
    LaunchedEffect(Unit) {
        onTabSelected(sources[0].id ?: "")
    }
    LazyRow {
        itemsIndexed(sources) { index, sourceItem ->

            Tab(
                selected = index == selectedIndex.intValue,
                onClick = {
                    onTabSelected(sourceItem.id ?: "")
                    selectedIndex.intValue = index
                },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = sourceItem.name ?: "",
                    color = Color.White,
                    modifier = if (index == selectedIndex.intValue) selectedModifier else
                        Modifier
                )

            }
        }
    }
}

@Preview
@Composable
private fun SourcesTabLazyRowPreview() {
    SourcesTabsLazyRow(
        listOf(
            SourcesItemEntity(name = "ABC News"),
            SourcesItemEntity(name = "Al-Jazeera News"),
            SourcesItemEntity(name = "BBC News"),
        )
    ) {

    }
}


@Preview
@Composable
private fun NewsScreenPreview() {
    NewsScreenContent()
}

@Composable
fun NewsList(
    articlesList: List<ArticlesItemEntity>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(articlesList) {
            NewsCard(articlesItem = it)
        }
    }
}

@Composable
fun NewsCard(articlesItem: ArticlesItemEntity, modifier: Modifier = Modifier) {
    Card(
        modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(0.9F)
            .border(1.dp, Color.White, RoundedCornerShape(12.dp))
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
        )
    ) {
        AsyncImage(
            model = articlesItem.urlToImage,
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            contentDescription = "News Article Image"
        )
        Text(
            text = articlesItem.title ?: "", color = Color.White, fontWeight = FontWeight.W700,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.by) + articlesItem.author,
                color = gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.fillMaxWidth(0.5F),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = articlesItem.publishedAt ?: "", color = gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.W500
            )
        }

    }
}

@Preview
@Composable
private fun NewsCardPreview() {
    NewsCard(
        ArticlesItemEntity(
            title = "40-year-old man falls 200 feet to his death while canyoneering at national park",
            author = "Jon Haworth",

            )
    )
}
