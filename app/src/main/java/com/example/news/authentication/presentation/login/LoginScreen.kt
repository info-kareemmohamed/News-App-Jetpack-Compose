package com.example.news.authentication.presentation.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.core.presentation.ui.theme.BlueGray
import com.example.news.core.presentation.ui.theme.LightGrayColor
import com.example.news.core.presentation.ui.theme.NewsTheme
import com.example.news.core.util.Dimens
import com.example.news.core.util.Dimens.MediumPadding_30

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    var isRememberMeChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.MediumPadding_20)
    ) {
        GreetingText()

        Spacer(modifier = Modifier.height(MediumPadding_30))

        UsernameField(username) { username = it }

        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_6))

        PasswordField(password, visibility, { visibility = it }) { password = it }

        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding_15))

        RememberMeRow(isChecked = isRememberMeChecked) { isRememberMeChecked = it }

        LoginButton { /* TODO: Handle login */ }

        ConnectionOptions(modifier = Modifier.align(Alignment.CenterHorizontally))

        SignUpText()
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
fun UsernameField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun PasswordField(
    value: String,
    visibility: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
        ),
        trailingIcon = {
            IconButton(
                onClick = { onVisibilityChange(!visibility) }
            ) {
                Icon(
                    imageVector = if (visibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Password Toggle"
                )
            }
        },
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation() // Hide password text if visibility is off
    )
}

@Composable
fun RememberMeRow(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
            text = "Forgot the password ?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.ExtraSmallPadding_15)
            .height(50.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        onClick = onClick
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = White,
        )
    }
}

@Composable
fun ConnectionOptions(modifier: Modifier = Modifier) {
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
        onClick = { /* TODO */ }
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


@Composable
fun SignUpText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "don't have an account ?",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = colorResource(R.color.text_medium),
        )
        Text(
            text = " Sign Up",
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
