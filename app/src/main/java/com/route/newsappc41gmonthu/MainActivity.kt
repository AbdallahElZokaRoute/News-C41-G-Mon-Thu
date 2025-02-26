package com.route.newsappc41gmonthu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.newsappc41gmonthu.categories.CategoriesScreen
import com.route.newsappc41gmonthu.news.NewsScreen
import com.route.newsappc41gmonthu.ui.theme.NewsAppC41GMonThuTheme
import com.route.newsappc41gmonthu.ui.theme.black

class MainActivity : ComponentActivity() {
    // 1- DiffUtil ->
    // 2- APIs & networking
    // 3- LaunchedEffect
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC41GMonThuTheme {
                NewsScreenContent()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(title: String, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(left = 8.dp, right = 8.dp),
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(
                    R.string.navigation_icon_of_side_menu
                )
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(
                    R.string.icon_search
                )
            )
        }
    )
}

@Preview
@Composable
private fun NewsToolbarPreview() {
    NewsToolbar(title = "General")
}

@Composable
fun NewsScreenContent(modifier: Modifier = Modifier) {

    Scaffold(containerColor = black, topBar = {
        NewsToolbar(title = "Business")
    }) { paddingValues ->
        paddingValues

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = CategoriesScreen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<CategoriesScreen> {
                CategoriesScreen(navController)
            }
            composable<NewsScreen> { navBackStackEntry ->
                val newsScreen = navBackStackEntry.toRoute<NewsScreen>()
                NewsScreen(newsScreen.categoryApiId)
            }

        }
    }
}
