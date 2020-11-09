package org.shaun.realmnotesapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.realm.Realm
import org.shaun.realmnotesapp.Dao.NotesDao
import org.shaun.realmnotesapp.Dao.RealmLiveData
import org.shaun.realmnotesapp.modelclass.NotesObject
import org.shaun.realmnotesapp.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    var repository: NotesRepository
    var allNotes: RealmLiveData<NotesObject>

    init {
        val notesDao = Realm.getDefaultInstance()
        repository = NotesRepository(NotesDao(notesDao))
        allNotes = repository.allNotes
    }

    fun updateByID(note: NotesObject) {
            repository.updateByID(note)
    }

    fun copyOrUpdate(note:NotesObject){
        repository.copyOrUpdate(note)
    }

}
