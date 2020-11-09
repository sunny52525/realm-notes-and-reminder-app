package org.shaun.realmnotesapp.repository

import android.util.Log
import org.shaun.realmnotesapp.Dao.NotesDao
import org.shaun.realmnotesapp.Dao.RealmLiveData
import org.shaun.realmnotesapp.modelclass.NotesObject

class NotesRepository(private  val notesDao: NotesDao){

    val allNotes: RealmLiveData<NotesObject> =notesDao.getAllNotes()

    fun updateByID(note: NotesObject) {
        notesDao.updateById(note, note.id)
        Log.d(TAG, "updateByID: $allNotes")
    }
    fun copyOrUpdate(note:NotesObject){
        notesDao.copyOrUpdate(note)
    }

    companion object {
        private const val TAG = "NotesRepository"
    }

}