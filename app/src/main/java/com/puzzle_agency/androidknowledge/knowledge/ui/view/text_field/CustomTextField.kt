package com.puzzle_agency.androidknowledge.knowledge.ui.view.text_field

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    style: TextFieldStyle = TextFieldStyle.Text,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(style != TextFieldStyle.Password)
    }

    val visualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    val keyboardType = when (style) {
        TextFieldStyle.Text -> KeyboardType.Text
        TextFieldStyle.Number -> KeyboardType.Number
        TextFieldStyle.Password -> KeyboardType.Password
        TextFieldStyle.Email -> KeyboardType.Email
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (style == TextFieldStyle.Password) {
                PasswordTrailingIcon(passwordVisible) { passwordVisible = !passwordVisible }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = shape,
        colors = colors,
        supportingText = supportingText,
        isError = isError
    )
}

@Composable
fun PasswordTrailingIcon(
    passwordVisible: Boolean,
    onClick: () -> Unit,
) {
    val image = if (passwordVisible) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    IconButton(onClick = onClick) {
        Icon(imageVector = image, contentDescription = null)
    }
}

enum class TextFieldStyle {
    Text, Number, Password, Email
}
