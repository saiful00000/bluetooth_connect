package com.shaiful.bluetoothconnect.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shaiful.bluetoothconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String?,
    onReload: (() -> Unit)? = null,
    onNavigationIcon: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bluetooth),
                    contentDescription = "bluetooth image",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                if (!title.isNullOrEmpty()) Text(text = title)
            }
        },
        navigationIcon = {
            if (onNavigationIcon != null) {
                IconButton(
                    onClick = {
                        onNavigationIcon()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Refresh",
                    )
                }
            }
        },
        actions = {
            if (onReload != null)
                IconButton(
                    onClick = {
                        onReload()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                    )
                }
        }
    )
}