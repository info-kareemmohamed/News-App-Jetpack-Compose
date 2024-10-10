package com.example.news.authentication.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun PasswordField(
    value: String,
    visibility: Boolean,
    label: String = "Password",
    onVisibilityChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
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