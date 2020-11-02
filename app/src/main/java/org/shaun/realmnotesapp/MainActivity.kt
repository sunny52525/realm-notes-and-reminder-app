package org.shaun.realmnotesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.shaun.realmnotesapp.modelclass.NotesObject
private  const val TAG="Main Activity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        new_note.setOnClickListener {
            startActivity(Intent(this,NewNoteActivity::class.java))
        }


        val realm=Realm.getDefaultInstance()
        realm.beginTransaction()
        val result=realm.where<NotesObject>().findAll()
        Log.d(TAG, "onCreate: **************************")
        Log.d(TAG, "onCreate: $result")
        Log.d(TAG, "onCreate: **************************")

    }
}
