package com.puzzle_agency.androidknowledge.knowledge.ui.view.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    length: Int = 4,
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val trimmed = it.trim()
            if (trimmed.length <= length) {
                onValueChange(trimmed)
            }
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(length) { index ->
                    val char = value.getOrNull(index)
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .border(
                                width = 0.5.dp,
                                color = Color(color = 0xFF797979),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        char?.let {
                            Text(
                                text = it.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                color = Color(color = 0xFFe5e5e5),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    )
}
