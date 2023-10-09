package com.shaiful.bluetoothconnect.ui.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.shaiful.bluetoothconnect.ui.viewmodels.BluetoothViewModel
import com.shaiful.bluetoothconnect.ui.widgets.AppTopAppBar
import com.shaiful.bluetoothconnect.ui.widgets.BluetoothDeviceTile

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScannerScreen(
    bluetoothViewModel: BluetoothViewModel,
    bluetoothAdapter: BluetoothAdapter,
    navHostController: NavHostController,
) {

    val deviceList = bluetoothViewModel.unpairedDevices.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "Devices",
                onNavigationIcon = {
                    navHostController.popBackStack()
                },
                onReload = {
                    bluetoothAdapter.startDiscovery()
                }
            )
        },
        content = { contentPadding ->
            LazyColumn(
                contentPadding = contentPadding
            ) {
                itemsIndexed(deviceList.value) { index, device ->
                    BluetoothDeviceTile(
                        device = device,
                        index = index,
                        onBond = {
                            bluetoothViewModel.pairThisDevice(device)
                        },
                        onUnBond = {
                            bluetoothViewModel.unPairThisDevice(device)
                        },
                        onConnect = {
                            bluetoothViewModel.connectThisDevice(device)
                        },
                    )
                }
            }
        }
    )
}