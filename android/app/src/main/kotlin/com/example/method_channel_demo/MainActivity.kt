package com.example.method_channel_demo

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMethodCodec

class MainActivity: FlutterActivity() 
{
    private val CHANNEL = "com.example.method_channel_demo/battery";





    override fun configureFlutterEngine(flutterEngine: FlutterEngine)
    {
        super.configureFlutterEngine(flutterEngine)


     val methodChannel =    MethodChannel(flutterEngine.dartExecutor.binaryMessenger,CHANNEL);

        methodChannel.setMethodCallHandler{ call,result ->

                if (call.method=="getBatteryLevel")
                {

                     var level = getBatteryLevel();

                     if(level!=-1)
                     {
                         result.success(level);
                     }else
                     {
                         result.error("UNAVAILABLE","Bettery level not found",null);
                     }


                }else
                {
                    result.notImplemented();
                }

        }





    }




    private fun getBatteryLevel(): Int {
        val batteryLevel: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(
                Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }

        return batteryLevel
    }
}
