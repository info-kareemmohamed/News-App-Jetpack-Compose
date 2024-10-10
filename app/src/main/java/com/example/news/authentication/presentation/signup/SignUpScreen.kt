package com.example.news.authentication.presentation.signup

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.authentication.presentation.common.AuthClickableText
import com.example.news.authentication.presentation.common.ButtonAuthentication
import com.example.news.authentication.presentation.common.ConnectionOptions
import com.example.news.authentication.presentation.common.PasswordField
import com.example.news.authentication.presentation.common.InputField
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens
import com.example.news.core.util.Dimens.MediumPadding_20
import com.example.news.core.util.Dimens.MediumPadding_24
import com.example.news.core.util.Dimens.MediumPadding_30

@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding_20)
    ) {
        GreetingText()

        Spacer(modifier = Modifier.height(MediumPadding_30))

        InputField(label = "Username", username) { username = it }

        Spacer(modifier = Modifier.height(MediumPadding_24))

        InputField(label = "Email", email) { email = it }

        Spacer(modifier = Modifier.height(MediumPadding_24))

        PasswordField(
            value = password,
            visibility = passordVisibility,
            onVisibilityChange = {passordVisibility =it},
            onValueChange = { password = it })

        Spacer(modifier = Modifier.height(MediumPadding_24))

        PasswordField(
            label = "Confirm Password",
            value = confirmPassword,
            visibility = confirmPasswordVisibility,
            onVisibilityChange = { confirmPasswordVisibility = it },
            onValueChange = { confirmPassword = it })

        Spacer(modifier = Modifier.height(MediumPadding_24))

        ButtonAuthentication("SignUp") {}

        ConnectionOptions(modifier = Modifier.align(Alignment.CenterHorizontally)) {}
        AuthClickableText("Already have an account ?", " Login") {}
    }
}

@Composable
fun GreetingText() {
    Text(
        text = "Hello!",
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 50.sp
    )

    Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))
    Text(
        text = "SignUp to get Started",
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(R.color.text_medium),
        fontSize = 18.sp,
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    NewsTheme {
        SignUpScreen()
    }
}