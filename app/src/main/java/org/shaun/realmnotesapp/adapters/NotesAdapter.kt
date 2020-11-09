package org.shaun.realmnotesapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.shaun.realmnotesapp.R
import org.shaun.realmnotesapp.modelclass.NotesObject
import kotlin.math.min

class NotesAdapter internal constructor(
    context: Context,private val listener:OnHolderClick
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var notes = emptyList<NotesObject>()

    interface OnHolderClick{
        fun notesClicked(note:NotesObject)
    }
    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val reminderOrNotesIcon: ImageView = itemView.findViewById(R.id.note_type_icon)
        val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        val noteDate: TextView = itemView.findViewById(R.id.note_date)
        val noteTime: TextView = itemView.findViewById(R.id.note_time)
        val noteContent: TextView = itemView.findViewById(R.id.note_content_rv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesAdapter.NotesViewHolder {
        val itemView = inflater.inflate(R.layout.each_note_rv, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun getItemCount(): Int = notes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        val currentNote = notes[position]

        Log.d("TAG", "onBindViewHolder: $currentNote")
        val currentNoteTime = getNoteTime(currentNote.dateAndTime)
        holder.noteTitle.text = currentNote.title
        holder.noteDate.text = currentNoteTime.first
        holder.noteTime.text = currentNoteTime.second
        holder.noteContent.text =
            currentNote.content.substring(0, min(400, currentNote.content.length)) + "......"
        if (currentNote.isReminder) {
            holder.reminderOrNotesIcon.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.reminder_icon))
        } else {
            holder.reminderOrNotesIcon.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_notes))
        }

        holder.itemView.setOnClickListener {
            listener.notesClicked(currentNote)
        }
    }

    private fun getNoteTime(dateAndTime: String): Pair<String, String> {
        val separated = dateAndTime.split(",")
        Log.d("TAG", "getNoteTime: $separated")
        return Pair(separated[0], separated[1])
    }

    internal fun setNotes(notes: List<NotesObject>) {
        this.notes = notes.reversed()
    }
}