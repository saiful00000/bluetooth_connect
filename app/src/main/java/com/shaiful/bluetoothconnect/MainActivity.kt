package com.shaiful.bluetoothconnect

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shaiful.bluetoothconnect.ui.routes.RouteNames
import com.shaiful.bluetoothconnect.ui.screens.DeviceScannerScreen
import com.shaiful.bluetoothconnect.ui.screens.HomeScreen
import com.shaiful.bluetoothconnect.ui.screens.MessagingScreen
import com.shaiful.bluetoothconnect.ui.theme.BluetoothConnectTheme
import com.shaiful.bluetoothconnect.ui.viewmodels.BluetoothViewModel
import com.shaiful.bluetoothconnect.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var bluetoothViewModel: BluetoothViewModel
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter

    val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    it.data.let { obj ->
                        var detail = obj?.getStringExtra("details")
                        // or whatever your result keys are
                    }
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothDeviceReceiver)
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BluetoothConnectTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                homeViewModel = hiltViewModel()
                bluetoothViewModel = hiltViewModel()
                bluetoothManager = ContextCompat.getSystemService<BluetoothManager>(context, BluetoothManager::class.java)!!
                bluetoothAdapter = bluetoothManager.adapter!!

                // Register for broadcasts when a device is discovered.
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                registerReceiver(bluetoothDeviceReceiver, filter)

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
                    if (!homeViewModel.checkBluetoothServiceEnabledOrNot(context)) {
                        val btIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        activityLauncher.launch(btIntent)
                    }

                    /*
                    * Get paired devices
                    * */
                    val devices = bluetoothAdapter.bondedDevices
                    if(devices != null){
                        bluetoothViewModel.addPairedDevices(devices)
                    }

                    /*
                    * Start discover new/unpaired devices
                    * */
                    bluetoothAdapter.startDiscovery()

                    /*
                    * Make this device discoverable to other devices
                    * */
                    val requestCode = 1;
                    val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                        putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                    }
                    startActivityForResult(discoverableIntent, requestCode)

                    onDispose {}
                }

                NavHost(
                    navController = navController,
                    startDestination = RouteNames.HomeScreen.name
                ) {
                    composable(RouteNames.HomeScreen.name) {
                        HomeScreen(
                            navController = navController,
                            bluetoothViewModel = bluetoothViewModel
                        )
                    }
                    composable(RouteNames.DeviceScannerScreen.name) {
                        DeviceScannerScreen(
                            bluetoothViewModel = bluetoothViewModel,
                            bluetoothAdapter = bluetoothAdapter,
                            navHostController = navController,
                        )
                    }
                    composable(RouteNames.MessagingScreen.name) {
                        MessagingScreen()
                    }
                }
            }
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val bluetoothDeviceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.i("MainActivity", "Bluetooth device receiver")
            val granted = homeViewModel.getBluetoothPermissionsToRequest().all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }

            if(!granted){
                Log.i("MainActivity", "Bluetooth permissions are not granted from onReceive")
                return
            }

            val action: String? = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address
                    val bondStatus = device?.bondState
                    if(device != null){
                        when(device.bondState){
                            BluetoothDevice.BOND_BONDED -> {
                                // Device is paired
                                // You can initiate a connection here if it's not connected already
                                bluetoothViewModel.addPairedDevice(device)
                            }
                            BluetoothDevice.BOND_BONDING -> {
                                // Device is in the process of pairing
                            }
                            BluetoothDevice.BOND_NONE -> {
                                // Device is not paired
                                bluetoothViewModel.addUnpairedDevice(device)
                            }
                        }
                    }
                }
            }
        }
    }
}