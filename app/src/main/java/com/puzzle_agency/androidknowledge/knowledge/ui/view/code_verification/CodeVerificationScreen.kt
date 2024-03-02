package com.puzzle_agency.androidknowledge.knowledge.ui.view.code_verification

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puzzle_agency.androidknowledge.knowledge.ui.view.text_field.CodeTextField

@Composable
fun CodeVerificationScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(onNavigateBack) }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = Color(color = 0xFF111111))
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "4-digit code",
                    color = Color(color = 0xFFDBDBDB),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Code sent to +385 123 4567 unless you already have an account",
                    color = Color(color = 0xFF797979),
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                var code by remember {
                    mutableStateOf("")
                }

                CodeTextField(value = code, onValueChange = { code = it })
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                BackButton { onNavigateBack() }

                Spacer(modifier = Modifier.width(4.dp))

                val context = LocalContext.current

                ContinueButton(modifier = Modifier.weight(1f)) {
                    Toast.makeText(context, "Continue click", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(color = 0xFF111111),
            scrolledContainerColor = Color(color = 0xFF111111)
        )
    )
}

@Composable
private fun ContinueButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(64.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color = 0xFF024fe6)
        )
    ) {
        Text(text = "Continue".uppercase(), color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color(color = 0xFF2b2b2b)
        ),
        modifier = Modifier.size(64.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back"
        )
    }
}
