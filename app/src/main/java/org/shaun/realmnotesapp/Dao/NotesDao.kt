package org.shaun.realmnotesapp.Dao

import io.realm.Realm
import io.realm.kotlin.where
import org.shaun.realmnotesapp.modelclass.NotesObject

class NotesDao: Dao<NotesObject> {

    constructor(db: Realm?) {
        this.db = db
    }

    fun getAllNotes():RealmLiveData<NotesObject>{
        return db?.where<NotesObject>()!!.findAllAsync().asLiveData()
    }

    fun findNoteById(id:Int):NotesObject{
       return db?.where<NotesObject>()!!.findAll()[id]!!
    }

    fun totalNotesCount()=db?.where<NotesObject>()!!.findAll().size.toLong()

}