package com.shaiful.bluetoothconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shaiful.bluetoothconnect.R
import com.shaiful.bluetoothconnect.ui.routes.RouteNames
import com.shaiful.bluetoothconnect.ui.viewmodels.BluetoothViewModel
import com.shaiful.bluetoothconnect.ui.widgets.AppTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel,
) {

    val pairedDevices by bluetoothViewModel.pairedDevices.collectAsState()

    Scaffold(
        topBar = {
            AppTopAppBar(title = "Connect", onReload = null)
        },
        content = { contentPadding ->

            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                PairedDevicesScreen(pairedDevices, bluetoothViewModel)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(RouteNames.DeviceScannerScreen.name)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Fav Icon")
            }
        }
    )
}