package com.shaiful.bluetoothconnect.ui.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaiful.bluetoothconnect.ui.viewmodels.BluetoothViewModel
import com.shaiful.bluetoothconnect.ui.widgets.AppTopAppBar
import com.shaiful.bluetoothconnect.ui.widgets.BluetoothDeviceTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScannerScreen(bluetoothViewModel: BluetoothViewModel) {

    val deviceList = bluetoothViewModel.unpairedDevices.collectAsState(initial = emptyList())

    Scaffold (
        topBar = {
            AppTopAppBar(title = "Devices")
        },
        content = {contentPadding ->
            LazyColumn(
                contentPadding = contentPadding
            ){
                items(deviceList.value) { device ->
                    BluetoothDeviceTile(device = device)
                }
            }
        }
    )
}