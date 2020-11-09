package org.shaun.realmnotesapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import org.shaun.realmnotesapp.Dao.NotesDao
import org.shaun.realmnotesapp.adapters.NotesAdapter
import org.shaun.realmnotesapp.fragment.FullNotesFragment
import org.shaun.realmnotesapp.modelclass.NotesObject
import org.shaun.realmnotesapp.viewModel.NotesViewModel


private const val TAG = "Main Activity"

private var darkMode = true

class MainActivity : AppCompatActivity(), NotesAdapter.OnHolderClick {
    private lateinit var notesViewModel: NotesViewModel
    private var db: RealmResults<NotesObject>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createChannel("reminder", "reminder")
        initTheme()
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)

        val adapter = NotesAdapter(this, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer { ok ->
            ok?.let {
                adapter.setNotes(it)
            }
        })

        val new_note = findViewById<ExtendedFloatingActionButton>(R.id.new_note)
        new_note.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
        }

        nightMode.setOnCheckedChangeListener { buttonView, isChecked ->
            val isNightTheme =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    darkMode = false
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    darkMode = true
                }
            }

            nightMode(darkMode)
            val themePreference:SharedPreferences=getSharedPreferences("userTheme", Context.MODE_PRIVATE)
            val editor=themePreference.edit()
            editor.putBoolean("userTheme", darkMode)
            editor.apply()
        }


        db = NotesDao(Realm.getDefaultInstance()).getAllNotesAll()
        db?.addChangeListener(RealmChangeListener {
            adapter.notifyDataSetChanged()
        })

    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply { setShowBadge(false) }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun notesClicked(note: NotesObject) {

        val fragmentManager: FragmentManager = supportFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
        val bundle = Bundle()
        bundle.putSerializable("note", note)
        val newCustomFragment = FullNotesFragment()
        newCustomFragment.arguments = bundle
        transaction.replace(R.id.main_activity, newCustomFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initTheme(){
        val themepref: SharedPreferences =getSharedPreferences("userTheme", Context.MODE_PRIVATE)
        val editor=themepref.edit()

        if(themepref.contains("userTheme")){
            darkMode = themepref.getBoolean("userTheme",false)
        }else{
            val isNightTheme =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            darkMode = isNightTheme== Configuration.UI_MODE_NIGHT_YES
            editor.putBoolean("userTheme",false)
            editor.apply()
        }
        nightMode(darkMode)

    }

    private fun nightMode(on:Boolean){
        nightMode.isChecked= on
        if(on)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

}
