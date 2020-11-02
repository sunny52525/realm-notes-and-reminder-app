package org.shaun.realmnotesapp.modelclass

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
open class NotesObject(
    @PrimaryKey
    var id:Long=0,
    var title:String="",
    var content:String=""
): RealmObject(){
    override fun toString(): String {
        
        return """
            id s $id,
            title is $title,
            content is $content
        """.trimIndent()
    }
}