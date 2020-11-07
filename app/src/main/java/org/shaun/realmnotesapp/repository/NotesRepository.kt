package org.shaun.realmnotesapp.repository

import org.shaun.realmnotesapp.Dao.NotesDao
import org.shaun.realmnotesapp.Dao.RealmLiveData
import org.shaun.realmnotesapp.modelclass.NotesObject

class NotesRepository(private val notesDao: NotesDao){

    val allNotes: RealmLiveData<NotesObject> =notesDao.getAllNotes()

}