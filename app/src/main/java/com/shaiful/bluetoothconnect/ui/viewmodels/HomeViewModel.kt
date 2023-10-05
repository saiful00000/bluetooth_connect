package com.shaiful.bluetoothconnect.ui.viewmodels

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _bluetoothPermissionGranted = MutableStateFlow<Boolean>(false)
    val bluetoothPermissionGranted: StateFlow<Boolean> = _bluetoothPermissionGranted.asStateFlow()

    private fun _logInfo(message: String) {
        Log.i("Home ViewModel", message)
    }

    fun onBluetoothPermissionResult(granted: Boolean) {
        _bluetoothPermissionGranted.value = granted;
    }

    fun checkALlBluetoothPermissionsAreGranted(context: Context) {
        val granted = getBluetoothPermissionsToRequest().all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        _bluetoothPermissionGranted.value = granted
    }

    fun checkBluetoothServiceEnabledOrNot(context: Context): Boolean {
        val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService<BluetoothManager>(context, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        if(bluetoothAdapter == null){
            _logInfo("Bluetooth service not available or disabled")
            return false;
        }

        return bluetoothAdapter.isEnabled
    }

    fun getBluetoothPermissionsToRequest(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            _logInfo("Version code is >= S")
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            _logInfo("Version code is < S")
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }
}