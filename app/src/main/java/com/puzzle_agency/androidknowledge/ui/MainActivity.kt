package com.puzzle_agency.androidknowledge.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.glance.LocalContext
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.ui.theme.AndroidKnowledgeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val SERVER_CLIENT_ID =
            "584138663477-7afm5mvfrs4hmq71vhn7g2f1ekjjb2q8.apps.googleusercontent.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidKnowledgeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //CodeVerificationScreen { onNavigateUp() }
                        val coroutineScope = rememberCoroutineScope()

                        GoogleLoginButton(
                            onClick = {
                                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                                    .setFilterByAuthorizedAccounts(false) // Query all google accounts on the device
                                    .setServerClientId(SERVER_CLIENT_ID)
                                    .build()

                                val request = GetCredentialRequest.Builder()
                                    .addCredentialOption(googleIdOption)
                                    .build()

                                val credentialManager = CredentialManager.create(this@MainActivity)

                                coroutineScope.launch {
                                    try {
                                        val result = credentialManager.getCredential(
                                            this@MainActivity,
                                            request
                                        )
                                        handleSignIn(result)
                                    } catch (e: GetCredentialException) {
                                        Log.e("MainActivity", "GetCredentialException", e)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        // TODO: Send [googleIdTokenCredential.idToken] to your backend
                        println("teoteoteo ${googleIdTokenCredential.idToken}")
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("MainActivity", "handleSignIn:", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("MainActivity", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("MainActivity", "Unexpected type of credential")
            }
        }
    }

    @Composable
    fun GoogleLoginButton(onClick: () -> Unit) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFF2A3039)),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                )

                Text(
                    text = "Continue with Google",
                    color = Color(color = 0xFFF6F6F6),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
