package com.shaiful.bluetoothconnect.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.shaiful.bluetoothconnect.ui.viewmodels.HomeViewModel
//val storyPosts by viewModel.storyPosts.collectAsStateWithLifecycle()
@Composable
fun PairedDevicesScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val allPermissionsGranted by homeViewModel.bluetoothPermissionGranted.collectAsState()

    homeViewModel.checkALlBluetoothPermissionsAreGranted(LocalContext.current)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = allPermissionsGranted.toString())
    }
}