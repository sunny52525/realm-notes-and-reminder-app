package org.shaun.realmnotesapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.shaun.realmnotesapp.R
import org.shaun.realmnotesapp.modelclass.NotesObject
import kotlin.math.abs


private const val TAG = "FullNotesFragment"

class FullNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_full_notes, container, false)

        detectSwipe(v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = this.arguments?.getSerializable("note") as NotesObject
//        Log.d(TAG, "onCreateView: $note")
        initViews(note)
    }
    private fun initViews(note: NotesObject) {
        val titleFullNote = view?.findViewById<TextView>(R.id.title_full_note)
        val dateFullNote = view?.findViewById<TextView>(R.id.date_full_note)
        val contentFullNote = view?.findViewById<TextView>(R.id.content_full_note)
        val isReminderFullNote = view?.findViewById<ImageView>(R.id.is_reminder_full_note)
        val reminderTimeFullNote = view?.findViewById<TextView>(R.id.reminder_time_full_note)
        titleFullNote?.text = note.title
        dateFullNote?.text = note.dateAndTime
        contentFullNote?.text = note.content
        if (note.isReminder) {
            isReminderFullNote?.visibility = View.VISIBLE
            reminderTimeFullNote?.visibility = View.VISIBLE
            Log.d(TAG, "initViews:${note.reminderTime.toString()} ")
            reminderTimeFullNote?.text = note.reminderTime.toString()
        }

    }

    private fun detectSwipe(v: View) {
        val gesture = GestureDetector(
            activity,
            object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    Log.i(TAG, "onFling has been called!")
                    val swipeMinDistance = 120
                    val swipeMaxOfPath = 250
                    val swipeThresholdVelocity = 200
                    try {
                        if (abs(e1.y - e2.y) > swipeMaxOfPath) return false
                        if (e1.x - e2.x > swipeMinDistance
                            && abs(velocityX) > swipeThresholdVelocity
                        ) {
                            Log.i(TAG, "Right to Left")
                        } else if (e2.x - e1.x > swipeMinDistance
                            && abs(velocityX) > swipeThresholdVelocity
                        ) {
                            Log.i(TAG, "Left to Right")
                            activity?.onBackPressed()
                        }
                    } catch (e: Exception) {

                    }
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })

        v.setOnTouchListener({ _, event -> gesture.onTouchEvent(event) })
    }


}