package org.shaun.realmnotesapp.Dao

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.shaun.realmnotesapp.modelclass.NotesObject


private const val TAG = "NotesDao"

class NotesDao : Dao<NotesObject> {

    constructor(db: Realm?) {
        this.db = db
    }

    fun getAllNotes(): RealmLiveData<NotesObject> {
        return db?.where<NotesObject>()!!.findAllAsync().asLiveData()
    }

    fun getAllNotesAll(): RealmResults<NotesObject> {
        return db?.where<NotesObject>()!!.findAllAsync()
    }

    fun findNoteById(id: Int): NotesObject {
        return db?.where<NotesObject>()!!.findAll()[id]!!
    }

    fun totalNotesCount() = db?.where<NotesObject>()!!.findAll().size.toLong()

    fun updateById(note: NotesObject, id: Long) {

        db?.executeTransaction {
            val res = db?.where<NotesObject>()?.equalTo("id", id)?.findAll()
            res?.setValue("title", note.title)
            res?.setValue("content", note.content)
            res?.setValue("isReminder", note.isReminder)
            res?.setValue("reminderTime", note.reminderTime)
        }

        Log.d(TAG, "updateById: $note")

    }

    fun deleteNote(id: Long) {
        val result = db?.where<NotesObject>()?.equalTo("id", id)?.findAll()
        db?.executeTransaction {
            result?.deleteAllFromRealm()
        }
    }


}