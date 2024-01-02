package com.puzzle_agency.androidknowledge.knowledge.ui.view.camera

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.puzzle_agency.androidknowledge.knowledge.ui.permission.PermissionRequester

@Composable
fun CaptureImageScreen() {
    val captureImageScreenState = rememberCaptureImageScreenState()
    val context = LocalContext.current

    PermissionRequester(permission = Manifest.permission.CAMERA) {
        println("permission grant result = $it")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CameraPreview(
                controller = captureImageScreenState.controller,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { captureImageScreenState.switchCamera() },
                modifier = Modifier.offset(16.dp, 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = "Switch camera"
                )
            }

            IconButton(
                onClick = {
                    captureImageScreenState.takePhoto(context) {}
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take photo"
                )
            }
        }
    }
}
