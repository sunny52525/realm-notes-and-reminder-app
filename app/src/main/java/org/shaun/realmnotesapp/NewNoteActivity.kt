package org.shaun.realmnotesapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment.OnButtonClickListener
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_note.*
import org.shaun.realmnotesapp.Dao.NotesDao
import org.shaun.realmnotesapp.modelclass.NotesObject
import org.shaun.realmnotesapp.notification.AlarmSchedule
import org.shaun.realmnotesapp.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


private const val TAG = "NewNoteActivity"

class NewNoteActivity : AppCompatActivity() {

    private var isReminder = false
    private var reminderDate: Date? = null
    private var dao: NotesDao?=null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm ")
        val currentDate = simpleDateFormat.format(Date())

        current_time_note.text = currentDate + isAmOrPM()

        is_reminder.setOnClickListener {
            if (is_reminder.isChecked) {
                addReminderTime(simpleDateFormat.parse(currentDate))
            } else {
                clearReminderTime()
            }
        }
        back_new_note.setOnClickListener {
            finish()
        }


        save_button.setOnClickListener {
            saveNote()
        }
    }

    private fun clearReminderTime() {
        reminder_time.text = ""
        is_reminder.isChecked = false
        isReminder = false
        reminderDate = null
    }

    private fun addReminderTime(currentDate: Date) {
        val dateTimeDialogFragment =
            SwitchDateTimeDialogFragment.newInstance(
                "Choose Reminder Time",
                "OK",
                "Cancel"
            )
        dateTimeDialogFragment.startAtCalendarView()
        dateTimeDialogFragment.set24HoursMode(false)

//        dateTimeDialogFragment.isCancelable=false
        dateTimeDialogFragment.minimumDateTime = currentDate
        dateTimeDialogFragment.maximumDateTime = GregorianCalendar(
            2025,
            Calendar.DECEMBER,
            31
        ).time

        dateTimeDialogFragment.setDefaultDateTime(
            currentDate
        )
        try {
            dateTimeDialogFragment.simpleDateMonthAndDayFormat = SimpleDateFormat(
                "dd MMMM",
                Locale.getDefault()
            )
        } catch (e: SimpleDateMonthAndDayFormatException) {
            Log.e(TAG, e.message!!)
        }


        dateTimeDialogFragment.setOnButtonClickListener(object : OnButtonClickListener {
            override fun onPositiveButtonClick(date: Date?) {
                Log.d(TAG, "onPositiveButtonClick: $date")
                reminder_time.text = date.toString()
                isReminder = true
                reminderDate = date

            }

            override fun onNegativeButtonClick(date: Date?) {
                clearReminderTime()

            }
        })

        dateTimeDialogFragment.show(supportFragmentManager, "dialog_time")
    }

    private fun saveNote() {
        val realm = Realm.getDefaultInstance()
        dao= NotesDao(realm)
        val content = note_content.text.toString()
        val title = title_note.text.toString()
        val id =  dao!!.totalNotesCount()+1
        val newNote = NotesObject()
        newNote.id = id
        newNote.content = content
        newNote.title = title
        newNote.dateAndTime = current_time_note.text.toString()
        newNote.isReminder = isReminder
        newNote.reminderTime = reminderDate

//        dao?.copyOrUpdate(newNote)
         NotesViewModel(application).copyOrUpdate(newNote)
        if (isReminder && reminderDate!=null)
            setAlarm()
        finish()
    }


    private fun setAlarm() {
        val intent = Intent(this, AlarmSchedule::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 101, intent, 0)
        val alarmManger = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        reminderDate?.time?.let { alarmManger.set(AlarmManager.RTC_WAKEUP, it, pendingIntent) }
    }

    private fun isAmOrPM(): String {
        val now = Calendar.getInstance()
        return if (now[Calendar.AM_PM] == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }
    }


}
