package org.shaun.realmnotesapp

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.shaun.realmnotesapp.modelclass.NotesObject

class NotesDao:Dao<NotesObject>{

    constructor(db: Realm?) {
        this.db = db
    }

    fun getAllNotes():RealmResults<NotesObject>{
        return db?.where<NotesObject>()!!.findAllAsync()
    }

    fun findNoteById(id:Int):NotesObject{
       return db?.where<NotesObject>()!!.findAll()[id]!!
    }

    fun totalNotesCount()=db?.where<NotesObject>()!!.findAll().size.toLong()

}