package com.nomakey.authenticator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nomakey.authenticator.ui.screens.AccountListScreen
import com.nomakey.authenticator.ui.theme.NomaKeyTheme

/**
 * Main entry point for NomaKey Authenticator
 *
 * This activity hosts the main navigation and screens
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NomaKeyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Add Navigation Component when you have multiple screens
                    AccountListScreen(
                        modifier = Modifier.fillMaxSize(),
                        onAddAccountClick = {
                            // TODO: Navigate to AddAccountScreen
                        },
                        onAccountClick = { account ->
                            // TODO: Navigate to AccountDetailScreen or show options
                        }
                    )
                }
            }
        }
    }
}

