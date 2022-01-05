package com.ghl.biz_home.liba_datapick.aldl

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ghl.biz_home.R
import com.ghl.router_annotation.Route
import com.ghl.sample.IMyAidlInterface

const val ACTION = "com.example.androidservice.ServerService"
private const val TAG = "ghl"

@Route("AidlActivity")
class AidlActivity : AppCompatActivity() {

    private var myAidlInterface: IMyAidlInterface? = null

    private val btnBind: Button by lazy {
        findViewById(R.id.btn_bind)
    }
    private val btnGetData: Button by lazy {
        findViewById(R.id.btn_getdata)
    }
    private val btnSendData: Button by lazy {
        findViewById(R.id.btn_sendData)
    }
    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private val mConn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            //连接成功之后，会传递一个远程的Binder对象过来
            //然后转化成本地接口对象
            Log.i(TAG, "onServiceConnected: $service")
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            myAidlInterface = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dial_activity)

        btnBind.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            createExplicitIntent(this, intent)?.apply {
                bindService(this, mConn, Context.BIND_AUTO_CREATE)
            }
        }


        btnGetData.setOnClickListener {
            myAidlInterface?.apply {
                val result = data

                textView.text = result
                Log.i(TAG, "onBtnGetdataClicked: $result")
            }
        }

        btnSendData.setOnClickListener {
            try {
                val ret = myAidlInterface!!.sendData("I send data from client!!")
                Log.i(TAG, "onBtnSendDataClicked: $ret")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myAidlInterface?.apply {
            unbindService(mConn)
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun createExplicitIntent(context: Context, implicitIntent: Intent): Intent? {
        val pm = context.packageManager
        val resolveInfo = pm.queryIntentServices(implicitIntent, 0)
        if (resolveInfo.size != 1) {
            return null
        }
        val serviceInfo = resolveInfo[0]
        val packageName = serviceInfo.serviceInfo.packageName
        val className = serviceInfo.serviceInfo.name
        val component = ComponentName(packageName, className)
        val explicitIntent = Intent(implicitIntent)
        explicitIntent.component = component
        return explicitIntent
    }


}