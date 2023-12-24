package com.puzzle_agency.androidknowledge.knowledge.auth.google

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

/**
 * Sample how to log in using google & Compose
 * 1. <string name="google_cloud_server_client_id">YOUR_ID</string>
 * 2. implementation 'com.google.android.gms:play-services-auth:19.2.0'
 * 3. Use the code below
 */

private fun Context.getGoogleLoginAuth(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("server_client_id")
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(this, gso)
}

@Composable
fun GoogleAuthSample(handleSignInResult: (Task<GoogleSignInAccount>?) -> Unit) {
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent =
                result.data ?: return@rememberLauncherForActivityResult handleSignInResult(null)

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            handleSignInResult(task)
        }

    val context = LocalContext.current

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = { startForResult.launch(context.getGoogleLoginAuth().signInIntent) }
        ) {
            Text(text = "Sign in with google")
        }
    }
}
