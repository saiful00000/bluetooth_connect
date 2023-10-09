package com.shaiful.bluetoothconnect.ui.widgets

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceTile(device: BluetoothDevice, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
            // You can add more information about the Bluetooth device here if needed
        }
    }
}