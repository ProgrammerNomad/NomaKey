package com.nomakey.authenticator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nomakey.authenticator.core.model.Account
import com.nomakey.authenticator.core.model.OtpType
import com.nomakey.authenticator.core.otp.Base32
import com.nomakey.authenticator.core.otp.TotpGenerator

/**
 * Main screen showing list of OTP accounts
 *
 * TODO: Connect to ViewModel and real data
 * TODO: Add swipe-to-delete
 * TODO: Add search/filter
 * TODO: Add navigation to AddAccount screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(
    modifier: Modifier = Modifier,
    onAddAccountClick: () -> Unit = {},
    onAccountClick: (Account) -> Unit = {}
) {
    // TODO: Replace with ViewModel
    val sampleAccounts = remember { emptyList<Account>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NomaKey") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddAccountClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Account")
            }
        }
    ) { paddingValues ->
        if (sampleAccounts.isEmpty()) {
            EmptyState(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleAccounts) { account ->
                    AccountItem(
                        account = account,
                        onClick = { onAccountClick(account) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔐",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Accounts Yet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the + button to add your first authenticator account",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountItem(
    account: Account,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Generate current OTP code
    var currentCode by remember { mutableStateOf("000000") }
    var remainingSeconds by remember { mutableStateOf(30) }

    // TODO: Add timer to update code
    LaunchedEffect(Unit) {
        try {
            val secret = Base32.decode(account.secret)
            if (account.otpType == OtpType.TOTP) {
                currentCode = TotpGenerator.generate(
                    secret = secret,
                    period = account.period,
                    digits = account.digits,
                    algorithm = account.algorithm.getHmacAlgorithm()
                )
                remainingSeconds = TotpGenerator.getRemainingSeconds(period = account.period)
            }
        } catch (e: Exception) {
            currentCode = "ERROR"
        }
    }

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Issuer
            Text(
                text = account.issuer,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Account name
            Text(
                text = account.accountName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // OTP Code
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentCode.chunked(3).joinToString(" "),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Timer indicator
                if (account.otpType == OtpType.TOTP) {
                    CircularProgressIndicator(
                        progress = remainingSeconds.toFloat() / account.period,
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

// TODO: Add preview
// @Preview(showBackground = true)
// @Composable
// fun AccountListScreenPreview() {
//     NomaKeyTheme {
//         AccountListScreen()
//     }
// }

