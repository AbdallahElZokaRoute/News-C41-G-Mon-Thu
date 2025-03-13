package com.route.newsappc41gmonthu.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.route.newsappc41gmonthu.NewsScreen
import com.route.newsappc41gmonthu.R
import com.route.newsappc41gmonthu.categories.Category
import com.route.newsappc41gmonthu.ui.theme.blackWithOpacity50

@Composable
fun CategoriesScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            CategoriesTitleText()
        }
        val list = Category.getCategoriesList()

        items(list.size) { position ->
            CategoryCard(category = list[position], isRight = position % 2 == 0, onCardClick = {
                navController.navigate(NewsScreen(it))
            }
            )
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    isRight: Boolean = true,
    onCardClick: (categoryID: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onCardClick(category.apiId ?: "")
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (isRight) {
                Image(
                    painter = painterResource(
                        id = category.imageResId ?: R.drawable.news_app_logo_image
                    ),
                    contentDescription = stringResource(R.string.news_category_image),
                    modifier = Modifier.fillMaxWidth(0.3F),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.weight(1F))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.app_name),
                        fontSize = 24.sp
                    )
                    ViewAllText(isRight = isRight)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.app_name),
                        fontSize = 24.sp
                    )
                    ViewAllText(isRight = isRight)
                }
                Spacer(modifier = Modifier.weight(1F))
                Image(
                    painter = painterResource(
                        id = category.imageResId ?: R.drawable.news_app_logo_image
                    ),
                    contentDescription = stringResource(R.string.news_category_image),
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .fillMaxHeight()
                        .scale(2F),
                )

            }
        }
    }
}

@Composable
fun ViewAllText(modifier: Modifier = Modifier, isRight: Boolean = true) {
    Row(
        modifier = modifier
            .background(blackWithOpacity50, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        if (isRight) {
            Text(
                text = stringResource(R.string.view_all),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.view_all_right_arrow),
                contentDescription = stringResource(
                    R.string.category_view_all
                )

            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.view_all_left_arrow),
                contentDescription = stringResource(
                    R.string.category_view_all
                )
            )
            Text(
                text = stringResource(R.string.view_all),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RightArrowCategoryCardPreview() {
    CategoryCard(category = Category.getCategoriesList().get(0), onCardClick = {})
}

@Composable
fun LeftArrowCategoryCard(category: Category, modifier: Modifier = Modifier) {

}

@Composable
fun CategoriesTitleText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.good_morning_here_is_some_news_for_you),
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.W500
    )
}


@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(rememberNavController())
}
