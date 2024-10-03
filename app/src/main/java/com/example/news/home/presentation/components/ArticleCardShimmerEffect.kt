package com.example.news.home.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news.core.presentation.common.shimmerEffect
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens.ArticleCardSize
import com.example.news.core.util.Dimens.ExtraSmallPadding_3
import com.example.news.core.util.Dimens.ExtraSmallPadding_6

@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding_3)
                .height(ArticleCardSize),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )



                Spacer(modifier = Modifier.width(ExtraSmallPadding_6))
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding_6))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )

            }

        }

    }

}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview() {
    NewsTheme {
        ArticleCardShimmerEffect()
    }
}