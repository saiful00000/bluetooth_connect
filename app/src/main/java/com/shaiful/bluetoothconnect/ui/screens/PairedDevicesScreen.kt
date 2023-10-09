package com.shaiful.bluetoothconnect.ui.screens

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shaiful.bluetoothconnect.ui.viewmodels.BluetoothViewModel
import com.shaiful.bluetoothconnect.ui.viewmodels.HomeViewModel
import com.shaiful.bluetoothconnect.ui.widgets.BluetoothDeviceTile

//val storyPosts by viewModel.storyPosts.collectAsStateWithLifecycle()
@Composable
fun PairedDevicesScreen(pairedDevices: List<BluetoothDevice>) {

    LazyColumn(
    ){
        items(pairedDevices) { device ->
            BluetoothDeviceTile(device = device)
        }
    }
}