package org.shaun.realmnotesapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.shaun.realmnotesapp.adapters.NotesAdapter
import org.shaun.realmnotesapp.viewModel.NotesViewModel

private  const val TAG="Main Activity"
class MainActivity : AppCompatActivity() {
    private lateinit var notesViewModel:NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel("reminder","reminder")
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        val adapter=NotesAdapter(this)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        notesViewModel=ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer { ok->
            ok?.let { adapter.setNotes(it) }
        })

        val new_note=findViewById<ExtendedFloatingActionButton>(R.id.new_note)
        new_note.setOnClickListener {
            startActivity(Intent(this,NewNoteActivity::class.java))
        }



//        val realm=Realm.getDefaultInstance()
//        val dao= NotesDao(realm)
//        val result=dao.getAllNotes()
//        Log.d(TAG, "onCreate: **************************")
//        Log.d(TAG, "onCreate: $result")
//        Log.d(TAG, "onCreate: **************************")
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
