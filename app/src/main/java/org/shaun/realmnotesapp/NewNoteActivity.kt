package org.shaun.realmnotesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_note.*
import org.shaun.realmnotesapp.modelclass.NotesObject

private const val TAG = "NewNoteActivity"

class NewNoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        save_button.setOnClickListener {
            saveNote()

        }
    }

    private fun saveNote() {
        val content = content.text.toString()
        val title = title_note.text.toString()
        val id = System.currentTimeMillis()
        val newNote = NotesObject()
        newNote.id = id
        newNote.content = content
        newNote.title = title
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val newNoteDB = realm.copyToRealmOrUpdate(newNote)
            Log.d(TAG, "onCreate: $newNote")
            realm.commitTransaction()
            realm.close()
        } catch (e: Exception) {
            Log.d(TAG, "onCreate: ${e.message}")
        }
        finish()
    }


}
