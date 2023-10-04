package com.shaiful.bluetoothconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shaiful.bluetoothconnect.ui.routes.RouteNames
import com.shaiful.bluetoothconnect.ui.screens.DeviceScannerScreen
import com.shaiful.bluetoothconnect.ui.screens.HomeScreen
import com.shaiful.bluetoothconnect.ui.screens.MessagingScreen
import com.shaiful.bluetoothconnect.ui.screens.PairedDevicesScreen
import com.shaiful.bluetoothconnect.ui.theme.BluetoothConnectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothConnectTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = RouteNames.HomeScreen.name){
                    composable(RouteNames.HomeScreen.name){
                        HomeScreen()
                    }
                    composable(RouteNames.PairedDevicesScreen.name){
                        PairedDevicesScreen()
                    }
                    composable(RouteNames.DeviceScannerScreen.name){
                        DeviceScannerScreen()
                    }
                    composable(RouteNames.MessagingScreen.name){
                        MessagingScreen()
                    }
                }
            }
        }
    }
}