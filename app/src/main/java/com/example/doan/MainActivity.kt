@file:Suppress("DEPRECATION")

package com.example.doan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

  var wifiManager: WifiManager? = null
    var listView: ListView? = null
     var buttonScan: Button? = null
    var size = 0
   var results: List<ScanResult>? = null
    var arrayList: ArrayList<String> = ArrayList()
     var adapter: ArrayAdapter<*>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonScan = findViewById(R.id.scanBtn)
        buttonScan?.setOnClickListener {

            scanWifi()
        }

        listView = findViewById(R.id.wifiList)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifiManager!!.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager!!.setWifiEnabled(true)
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        listView?.setAdapter(adapter)
        scanWifi()
    }

    private fun scanWifi() {
        arrayList.clear()
        registerReceiver(wifi_receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager?.startScan()
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show()
    }

    var wifi_receiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(c: Context, intent: Intent) {
            results = wifiManager?.getScanResults()
            unregisterReceiver(this)

            for (scanResult in (results as MutableList<ScanResult>?)!!) {
                arrayList.add(scanResult.SSID + " - " + scanResult.level)
                adapter?.notifyDataSetChanged()
            }
        }
    }

}

//import android.R
//import android.app.Activity
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.net.wifi.ScanResult
//import android.net.wifi.WifiManager
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.util.Log
//import android.view.View
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.ListView
//import android.widget.TextView
//import android.widget.Toast
//
//import java.util.ArrayList
//
//class  MainActivity : AppCompatActivity() {
//
//
//     var wifi: WifiManager? = null
//     var lv: ListView? = null
//     var buttonScan: Button? = null
//     var size = 0
//     var results: List<ScanResult>? = null
//
//     var ITEM_KEY = "key"
//     var arraylist = ArrayList<String>()
//     var adapter: ArrayAdapter<*>? = null
//
//     var wifi_receiver: BroadcastReceiver = object : BroadcastReceiver() {
//
//        override fun onReceive(c: Context, intent: Intent) {
//            Log.d("WifScanner", "onReceive")
//            results = wifi?.scanResults
//            size = results?.size ?:
//            unregisterReceiver(this)
//
//            try {
//                while (size >= 0) {
//                    size--
//                    results?.get(size)?.SSID?.let { arraylist.add(it) }
//                    adapter?.notifyDataSetChanged()
//                }
//            } catch (e: Exception) {
//                Log.w("WifScanner", "Exception: $e")
//
//            }
//
//
//        }
//    }
//
//    /* Called when the activity is first created. */
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        actionBar!!.title = "Widhwan Setup Wizard"
//
//        setContentView(R.layout.activity_main)
//
//        buttonScan = findViewById(R.id.scan) as Button
//        buttonScan?.setOnClickListener(this)
//        lv = findViewById(R.id.wifilist) as ListView
//
//
//        wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        if (wifi.isWifiEnabled == false) {
//            Toast.makeText(applicationContext, "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show()
//            wifi.isWifiEnabled = true
//        }
//        this.adapter = ArrayAdapter(this, R.layout.simple_list_item_1, arraylist)
//        lv.adapter = this.adapter
//
//        scanWifiNetworks()
//    }
//
//    override fun onClick(view: View) {
//        scanWifiNetworks()
//    }
//
//    private fun scanWifiNetworks() {
//
//        arraylist.clear()
//        registerReceiver(wifi_receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
//
//        wifi.startScan()
//
//        Log.d("WifScanner", "scanWifiNetworks")
//
//        Toast.makeText(this, "Scanning....", Toast.LENGTH_SHORT).show()
//
//    }
//
//}
