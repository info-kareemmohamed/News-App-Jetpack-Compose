package com.example.news.bookmark.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.news.R
import com.example.news.core.domain.model.Article
import com.example.news.core.presentation.common.ArticlesList
import com.example.news.core.presentation.navigation.Route
import com.example.news.core.util.Dimens.MediumPadding_24


@Composable
fun BookmarkScreen(
    articles: List<Article>,
    navigate: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(MediumPadding_24)
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.text_title)
        )

        Spacer(modifier = Modifier.height(MediumPadding_24))

        ArticlesList(
            articles = articles,
            onClick = { article -> navigate(article.url) },
        )
    }

}