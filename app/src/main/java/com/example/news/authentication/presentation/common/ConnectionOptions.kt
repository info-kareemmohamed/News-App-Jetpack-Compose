package com.example.news.authentication.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.core.presentation.ui.theme.BlueGray
import com.example.news.core.presentation.ui.theme.LightGrayColor
import com.example.news.core.util.Dimens

@Composable
fun ConnectionOptions(modifier: Modifier = Modifier,onConnect: () -> Unit) {
    Text(
        modifier = modifier,
        text = "or connect with",
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(R.color.text_medium),
        fontSize = 14.sp
    )

    Button(
        modifier = modifier
            .padding(vertical = Dimens.ExtraSmallPadding_15)
            .width(174.dp)
            .height(48.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightGrayColor,
            contentColor = Color.Transparent
        ),
        onClick = {onConnect()}
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding_6))
        Text(
            text = "Google",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = BlueGray
        )
    }
}