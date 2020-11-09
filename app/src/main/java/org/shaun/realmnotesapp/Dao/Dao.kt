package org.shaun.realmnotesapp.Dao

import android.util.Log
import io.realm.Realm
import io.realm.RealmModel
import org.shaun.realmnotesapp.modelclass.NotesObject

private const val TAG = "Dao"

abstract class Dao<T : RealmModel?> {
    protected var db: Realm? = null
    fun copyOrUpdate(note: NotesObject) {
        Log.d(TAG, "copyOrUpdate: $note")
        if (db!!.isInTransaction) {
            db?.copyToRealm(note)
        } else {
            try {
                db?.beginTransaction()
                db?.copyToRealm(note)
                db?.commitTransaction()
            } catch (e: Exception) {
                db?.cancelTransaction()
                throw e
            }
        }
    }

}