package com.shaiful.bluetoothconnect

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shaiful.bluetoothconnect.ui.routes.RouteNames
import com.shaiful.bluetoothconnect.ui.screens.DeviceScannerScreen
import com.shaiful.bluetoothconnect.ui.screens.HomeScreen
import com.shaiful.bluetoothconnect.ui.screens.MessagingScreen
import com.shaiful.bluetoothconnect.ui.screens.PairedDevicesScreen
import com.shaiful.bluetoothconnect.ui.theme.BluetoothConnectTheme
import com.shaiful.bluetoothconnect.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    it.data.let { obj ->
                        var detail =   obj?.getStringExtra("details")
                        // or whatever your result keys are
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothConnectTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = hiltViewModel()

                /*
                * Permission request result callback
                * */
                val bluetoothPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { grantedMap ->
                        val allPermissionsGranted = grantedMap.values.all { it }
                        Log.i("Permission results", "$grantedMap")
                        homeViewModel.onBluetoothPermissionResult(allPermissionsGranted)
                    }
                )

                DisposableEffect(Unit) {
                    /*
                    * request the permissi
                    * */
                    bluetoothPermissionResultLauncher.launch(
                        homeViewModel.getBluetoothPermissionsToRequest()
                    )

                    /*
                    * Request to enable bluetooth service
                    * */
                    if(!homeViewModel.checkBluetoothServiceEnabledOrNot(context)){
                        val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        activityLauncher.launch(btIntent)
                    }

                    onDispose {}
                }

                NavHost(
                    navController = navController,
                    startDestination = RouteNames.HomeScreen.name
                ) {
                    composable(RouteNames.HomeScreen.name) {
                        HomeScreen()
                    }
                    composable(RouteNames.PairedDevicesScreen.name) {
                        PairedDevicesScreen()
                    }
                    composable(RouteNames.DeviceScannerScreen.name) {
                        DeviceScannerScreen()
                    }
                    composable(RouteNames.MessagingScreen.name) {
                        MessagingScreen()
                    }
                }
            }
        }
    }
}