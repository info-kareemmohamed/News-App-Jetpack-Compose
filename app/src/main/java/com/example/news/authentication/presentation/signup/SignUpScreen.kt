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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.authentication.presentation.common.AuthClickableText
import com.example.news.authentication.presentation.common.ButtonAuthentication
import com.example.news.authentication.presentation.common.ConnectionOptions
import com.example.news.authentication.presentation.common.ErrorText
import com.example.news.authentication.presentation.common.PasswordField
import com.example.news.authentication.presentation.common.InputField
import com.example.news.authentication.presentation.signup.mvi.SignUpIntent
import com.example.news.authentication.presentation.signup.mvi.SignUpViewModel
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens
import com.example.news.core.util.Dimens.ExtraSmallPadding_10
import com.example.news.core.util.Dimens.MediumPadding_20
import com.example.news.core.util.Dimens.MediumPadding_30

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val state = viewModel.signUpState.collectAsState().value

    LaunchedEffect(viewModel.signUpSuccessfully) {
        viewModel.signUpSuccessfully.collect {
            if (it) navigateToHomeScreen()
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding_20)
    ) {
        GreetingText()

        Spacer(modifier = Modifier.height(MediumPadding_30))

        InputField(
            label = "User Name",
            value = state.username,
            state.usernameErrorMessage
        ) { viewModel.onIntent(SignUpIntent.UsernameChanged(it)) }
        ErrorText(error = state.usernameErrorMessage)

        Spacer(modifier = Modifier.height(ExtraSmallPadding_10))

        InputField(label = "Email", state.email, state.emailErrorMessage) {
            viewModel.onIntent(
                SignUpIntent.EmailChanged(it)
            )
        }

        ErrorText(error = state.emailErrorMessage)

        Spacer(modifier = Modifier.height(ExtraSmallPadding_10))

        PasswordField(
            value = state.password,
            visibility = state.isPasswordVisible,
            error = state.passwordErrorMessage,
            onVisibilityChange = { viewModel.onIntent(SignUpIntent.PasswordVisibilityChanged(it)) },
            onValueChange = { viewModel.onIntent(SignUpIntent.PasswordChanged(it)) })
        ErrorText(error = state.passwordErrorMessage)

        Spacer(modifier = Modifier.height(ExtraSmallPadding_10))

        PasswordField(
            label = "Confirm Password",
            value = state.confirmPassword,
            visibility = state.isConfirmPasswordVisible,
            error = state.confirmPasswordErrorMessage,
            onVisibilityChange = {
                viewModel.onIntent(
                    SignUpIntent.ConfirmPasswordVisibilityChanged(
                        it
                    )
                )
            },
            onValueChange = { viewModel.onIntent(SignUpIntent.ConfirmPasswordChanged(it)) })
        ErrorText(error = state.confirmPasswordErrorMessage)

        Spacer(modifier = Modifier.height(ExtraSmallPadding_10))

        ButtonAuthentication("SignUp",state.isLoading) {
            viewModel.onIntent(SignUpIntent.Submit)
        }
        ErrorText(error = state.errorMessage)

        ConnectionOptions(modifier = Modifier.align(Alignment.CenterHorizontally)) {}
        AuthClickableText("Already have an account ?", " Login") {
            navigateToLoginScreen()
        }
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
        SignUpScreen(
            navigateToLoginScreen = {},
            navigateToHomeScreen = {}
        )
    }
}