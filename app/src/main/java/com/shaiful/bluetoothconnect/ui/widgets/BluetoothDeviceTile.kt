package com.shaiful.bluetoothconnect.ui.widgets

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceTile(
    device: BluetoothDevice,
    index: Int,
    onBond: () -> Unit,
    onUnBond: () -> Unit,
    onConnect: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 8.dp, 8.dp)
        ) {
            Text(
                text = "$index"
            )
            Text(
                text = "Name: ${device.name ?: "Unknown"}",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Address: ${device.address ?: "N/A"}"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                IconButton(
                    onClick = {
                        onBond()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        onUnBond()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = {
                        onConnect()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Call,
                        contentDescription = null
                    )
                }
            }
        }
    }
}