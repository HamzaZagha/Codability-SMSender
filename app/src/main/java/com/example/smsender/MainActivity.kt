package com.example.smsender

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.EditText
import android.widget.Scroller
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.SEND_SMS), 15)
        }

        val smsManager = SmsManager.getDefault() as SmsManager

        val sendButton = findViewById<Button>(R.id.button1)
        val messageText = findViewById<EditText>(R.id.editText)
        val numbersText = findViewById<EditText>(R.id.editText2)
        val logs = findViewById<EditText>(R.id.logs)
        logs.isFocusable = false
        logs.clearFocus()
        logs.keyListener = null
        logs.isCursorVisible = false

        var ct=0

        sendButton.setOnClickListener {
            val numbers = numbersText.text.split("\n", ",")
            for(num in numbers){
                num.trim()
                ct+=1
                if(num.length >= 10){
                    logs.append("#$ct: Sending to $num\n")
                    println("#$ct: Sending to $num")

                    smsManager.sendTextMessage(num, null, messageText.text.toString(), null, null)
                }else{
                    logs.append("#$ct: Error! Number=$num\n")
                    println("#$ct: Error! Number=$num")
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
