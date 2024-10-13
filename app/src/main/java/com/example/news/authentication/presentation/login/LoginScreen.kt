package com.example.news.authentication.presentation.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.authentication.presentation.common.AuthClickableText
import com.example.news.authentication.presentation.common.ButtonAuthentication
import com.example.news.authentication.presentation.common.ConnectionOptions
import com.example.news.authentication.presentation.common.ErrorText
import com.example.news.authentication.presentation.common.PasswordField
import com.example.news.authentication.presentation.common.InputField
import com.example.news.authentication.presentation.login.mvi.LoginIntent
import com.example.news.authentication.presentation.login.mvi.LoginViewModel
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens
import com.example.news.core.util.Dimens.MediumPadding_30


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit = {},
    navigateToSignUpScreen: () -> Unit = {},
    navigateToForgotPasswordScreen: () -> Unit = {}
) {
    val state = viewModel.loginState.collectAsState()


    LaunchedEffect(viewModel.loginSuccessfully) {
        viewModel.loginSuccessfully.collect {
            if (it) navigateToHomeScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumPadding_20)
    ) {
        GreetingText()

        Spacer(modifier = Modifier.height(MediumPadding_30))

        InputField(
            label = "Email",
            value = state.value.email,
            error = state.value.emailErrorMessage
        ) { viewModel.onIntent(LoginIntent.EmailChanged(it)) }

        ErrorText(error = state.value.emailErrorMessage)

        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))

        PasswordField(
            state.value.password,
            state.value.isPasswordVisible,
            error = state.value.passwordErrorMessage,
            onVisibilityChange = { viewModel.onIntent(LoginIntent.VisibilityChanged(it)) },
            onValueChange = {
                viewModel.onIntent(LoginIntent.PasswordChanged(it))
            })

        ErrorText(error = state.value.passwordErrorMessage)
        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_15))

        RememberMeRow(isChecked = state.value.isRememberMeChecked,
            onCheckedChange = { viewModel.onIntent(LoginIntent.RememberMeChanged(it)) },
            onForgetPasswordClick = { navigateToForgotPasswordScreen() }
        )

        ErrorText(error = state.value.errorMessage)
        ButtonAuthentication(
            "Login",
            state.value.isLoading
        ) { viewModel.onIntent(LoginIntent.LoginClicked) }

        ConnectionOptions(modifier = Modifier.align(Alignment.CenterHorizontally)) {}

        AuthClickableText("don't have an account ?", " Sign Up") { navigateToSignUpScreen() }
    }
}

@Composable
fun GreetingText() {
    Text(
        text = "Hello",
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
        color = colorResource(id = R.color.text_title),
        fontSize = 50.sp
    )
    Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))
    Text(
        text = "Again!",
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 50.sp
    )
    Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))
    Text(
        text = "Welcome back, you've \nbeen missed",
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(R.color.text_medium),
        fontSize = 18.sp,
    )
}


@Composable
fun RememberMeRow(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onForgetPasswordClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                modifier = Modifier.size(30.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onBackground,
                    checkmarkColor = White
                )
            )
            Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding_3))
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.text_medium),
                fontSize = 14.sp
            )
        }
        Text(
            modifier = Modifier.clickable { onForgetPasswordClick() },
            text = "Forgot the password ?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    NewsTheme {
        LoginScreen()
    }
}
