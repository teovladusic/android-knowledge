package com.puzzle_agency.androidknowledge.knowledge.ui.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.puzzle_agency.androidknowledge.knowledge.ui.view.dialog.AlertActionDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequester(
    permission: String,
    rationaleTitle: String = "Permission",
    rationaleDescription: String = "You have to grant permission to use this feature",
    onPermissionGrantResult: (isGranted: Boolean) -> Unit
) {
    val permissionState = rememberPermissionState(permission)

    var showRationale by remember {
        mutableStateOf(false)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        onPermissionGrantResult(it)
    }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status != PermissionStatus.Granted) {
            if (permissionState.status.shouldShowRationale) {
                showRationale = true
            } else {
                permissionLauncher.launch(permission)
            }
        }
    }

    if (showRationale) {
        AlertActionDialog(
            title = rationaleTitle,
            description = rationaleDescription,
            actions = {
                Row(horizontalArrangement = Arrangement.End) {
                    TextButton(
                        onClick = { showRationale = false }
                    ) {
                        Text(text = "Cancel")
                    }

                    TextButton(
                        onClick = {
                            showRationale = false
                            permissionLauncher.launch(permission)
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            },
            onDismissRequest = { showRationale = false }
        )
    }
}
