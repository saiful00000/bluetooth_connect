package com.shaiful.bluetoothconnect.ui.viewmodels

import android.Manifest
import android.app.Activity
import android.content.Context
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
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _bluetoothPermissionGranted = MutableStateFlow<Boolean>(false)
    val bluetoothPermissionGranted: StateFlow<Boolean> = _bluetoothPermissionGranted.asStateFlow()

    fun checkBluetoothPermissions(context: Context) {
        Log.i("Blue Permissions", "Entered to the permission method")
        var permissions: Array<String>;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            Log.i("Blue Permissions", "Version code is >= S")
            permissions = arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        }else{
            Log.i("Blue Permissions", "Version code is < S")
            permissions = arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }

        val allPermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!allPermissionsGranted) {
            // Request permissions if they are not already granted
            ActivityCompat.requestPermissions(
                context as Activity,
                permissions,
                PackageManager.PERMISSION_GRANTED
            )
        }

        _bluetoothPermissionGranted.value = allPermissionsGranted
    }

}