package com.shaiful.bluetoothconnect.ui.viewmodels

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor() : ViewModel() {

    private val _unpairedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val unpairedDevices: StateFlow<List<BluetoothDevice>> = _unpairedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val pairedDevices: StateFlow<List<BluetoothDevice>> = _pairedDevices.asStateFlow()

    private fun logInfo(message: String) {
        Log.i("Bluetooth ViewModel", message)
    }

    fun addUnpairedDevice(device: BluetoothDevice) {
        val previousDevices = _unpairedDevices.value.toMutableList()
        previousDevices.add(device)
        _unpairedDevices.value = previousDevices.toSet().toList()
        logInfo("New device added to unpaired device list. device name -> $device")
    }

    fun addPairedDevices(devices: Set<BluetoothDevice>) {
        _pairedDevices.value = devices.toList()
        logInfo("New device added to paired device list. device name -> $devices")
    }

    fun addPairedDevice(device: BluetoothDevice) {
        val previousDevices = _pairedDevices.value.toMutableList().toMutableSet()
        previousDevices.add(device)
        _pairedDevices.value = previousDevices.toList()
        logInfo("New device added to paired device list. device name -> $device")
    }

    @SuppressLint("MissingPermission")
    fun pairThisDevice(device: BluetoothDevice) {
        val pairResult = device.createBond()
        logInfo("bluetooth pair result for ${device.address} is = $pairResult")
    }

    fun unPairThisDevice(device: BluetoothDevice) {
//        val pairResult = device.createBond()
        logInfo("bluetooth pair result for ${device.address} is = ")
    }

    fun connectThisDevice(device: BluetoothDevice) {
//        val pairResult = device.conn()
        logInfo("bluetooth pair result for ${device.address} is = ")
    }

}