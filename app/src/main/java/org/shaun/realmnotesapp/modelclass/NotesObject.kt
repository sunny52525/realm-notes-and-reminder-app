package org.shaun.realmnotesapp.modelclass

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NotesObject(
    @PrimaryKey
    var id:Long=0,
    var title:String="",
    var content:String="",
    var isReminder:Boolean=false,
    var dateAndTime:String="",
    var reminderTime: Date?=null
): RealmObject(){
    override fun toString(): String {
        
        return """
            \\\\\\\\\\\\\\\\\\\\\\\\\\
            \n
            id s $id,
            title is $title,
            content is $content
            isreminder $isReminder
            dateAndTime $dateAndTime
            reminder Time is $reminderTime
            \\\\\\\\\\\\\\\\\
        """.trimIndent()
    }
}