package com.example.news.authentication.presentation.forgot_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news.R
import com.example.news.authentication.presentation.common.ButtonAuthentication
import com.example.news.authentication.presentation.common.ErrorText
import com.example.news.authentication.presentation.common.InputField
import com.example.news.core.util.Dimens.ExtraSmallPadding_15
import com.example.news.core.util.Dimens.ExtraSmallPadding_6
import com.example.news.core.util.Dimens.IconSize
import com.example.news.core.util.Dimens.MediumPadding_20
import com.example.news.core.util.Dimens.MediumPadding_24

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToLoginScreen: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding_24),
    ) {


        Icon(
            modifier = Modifier
                .size(IconSize)
                .clickable { onBackClick() },
            painter = painterResource(R.drawable.ic_back_arrow),
            tint = colorResource(id = R.color.text_medium),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(MediumPadding_20))

        Text(
            text = "Forgot\nPassword ?",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colorResource(id = R.color.text_medium)
        )
        Spacer(modifier = Modifier.height(ExtraSmallPadding_6))

        Text(
            text = "Don’t worry! it happens. Please enter the\naddress associated with your account.",
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium)
        )

        Spacer(modifier = Modifier.height(ExtraSmallPadding_15))

        InputField(error = viewModel.errorMessage, label = "Email", value = viewModel.email) {
            viewModel.onEmailChange(it)
        }
        ErrorText(error = viewModel.errorMessage)
        if (viewModel.successMessage != null)
            Text(
                text = viewModel.successMessage ?: "", color = Color.Green,
                modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        Spacer(modifier = Modifier.weight(1f))
        ButtonAuthentication(
            loading = viewModel.isLoading,
            text = if (viewModel.successMessage == null) "Submit" else "Go To LogIn",
            onClick = {
                if (viewModel.successMessage != null)
                    navigateToLoginScreen()
                else
                    viewModel.forgotPassword()

            }
        )

    }
}

