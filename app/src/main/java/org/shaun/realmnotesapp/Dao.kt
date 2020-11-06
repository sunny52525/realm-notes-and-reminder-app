package org.shaun.realmnotesapp

import io.realm.Realm
import io.realm.RealmModel
import org.shaun.realmnotesapp.modelclass.NotesObject


abstract class Dao<T : RealmModel?> {
    protected var db: Realm? =null
    fun copyOrUpdate(note: NotesObject){
        if(db!!.isInTransaction){
            db?.copyToRealm(note)
        }
        try {
        db?.beginTransaction()
        db?.copyToRealm(note)
        db?.commitTransaction()
        }catch (e: Exception){
            db?.cancelTransaction()
            throw e
        }
    }

}