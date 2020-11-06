package org.shaun.realmnotesapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.realm.Realm
private  const val TAG="Main Activity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel("reminder","reminder")


        val new_note=findViewById<ExtendedFloatingActionButton>(R.id.new_note)
        new_note.setOnClickListener {
            startActivity(Intent(this,NewNoteActivity::class.java))
        }



        val realm=Realm.getDefaultInstance()
        val dao=NotesDao(realm)
        val result=dao.getAllNotes()
        Log.d(TAG, "onCreate: **************************")
        Log.d(TAG, "onCreate: $result")
        Log.d(TAG, "onCreate: **************************")
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply { setShowBadge(false) }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
