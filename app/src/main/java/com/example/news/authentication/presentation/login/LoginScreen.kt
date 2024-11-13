package com.example.news.authentication.presentation.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.authentication.presentation.common.AuthClickableText
import com.example.news.authentication.presentation.common.ButtonAuthentication
import com.example.news.authentication.presentation.common.ConnectionOptions
import com.example.news.authentication.presentation.common.ErrorText
import com.example.news.authentication.presentation.common.InputField
import com.example.news.authentication.presentation.common.PasswordField
import com.example.news.authentication.presentation.common.createGoogleSignInOptions
import com.example.news.authentication.presentation.common.handleGoogleSignInResult
import com.example.news.authentication.presentation.login.mvi.LoginIntent
import com.example.news.authentication.presentation.login.mvi.LoginState
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens
import com.example.news.core.util.Dimens.MediumPadding_30
import com.google.android.gms.auth.api.signin.GoogleSignIn


@Composable
fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    loginSuccessfully: Boolean,
    navigateToHomeScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit = {}
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        handleGoogleSignInResult(it) { credential ->
            onIntent(LoginIntent.GoogleSignInClicked(credential))
        }
    }


    LaunchedEffect(loginSuccessfully) {
        if (loginSuccessfully) navigateToHomeScreen()
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
            value = state.email,
            error = state.emailErrorMessage
        ) { onIntent(LoginIntent.EmailChanged(it)) }

        ErrorText(error = state.emailErrorMessage)

        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))

        PasswordField(
            state.password,
            state.isPasswordVisible,
            error = state.passwordErrorMessage,
            onVisibilityChange = { onIntent(LoginIntent.VisibilityChanged(it)) },
            onValueChange = {
                onIntent(LoginIntent.PasswordChanged(it))
            })

        ErrorText(error = state.passwordErrorMessage)
        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_15))

        RememberMeRow(isChecked = state.isRememberMeChecked,
            onCheckedChange = { onIntent(LoginIntent.RememberMeChanged(it)) },
            onForgetPasswordClick = { navigateToForgotPasswordScreen() }
        )

        ErrorText(error = state.errorMessage)
        ButtonAuthentication(
            "Login",
            state.isLoading
        ) { onIntent(LoginIntent.LoginClicked) }

        ConnectionOptions(
            isGoogle = state.isLoadingGoogle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            val googleSignInClient = GoogleSignIn.getClient(context, createGoogleSignInOptions())
            launcher.launch(googleSignInClient.signInIntent)
        }

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
        LoginScreen(
            state = LoginState(),
            onIntent = {},
            loginSuccessfully = false,
            navigateToHomeScreen = {},
            navigateToSignUpScreen = {}
        )
    }
}
