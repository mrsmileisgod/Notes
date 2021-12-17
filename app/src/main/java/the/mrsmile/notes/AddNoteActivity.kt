package the.mrsmile.notes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import the.mrsmile.notes.databinding.ActivityAddNoteBinding
import kotlin.properties.Delegates


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var database: DatabaseReference
    private var index by Delegates.notNull<Long>()


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        index = intent.getLongExtra("index",9)

    }

    private fun writeData(title: String, desc: String) {
        val note = Items(title, desc)
        database.child("notes").child(index.toString()).setValue(note)
    }

    private fun getTitlee(): String {
        return binding.eTtitle.text.toString()
    }

    private fun getDesc(): String {
        return binding.eTdesc.text.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val title = getTitlee()
        val desc = getDesc()
        if (title != "" && desc != "") {
            writeData(title, desc)

        } else {
            Snackbar.make(
                this,
                binding.root,
                "Invalid Note Provided !",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
        binding.eTdesc.text = null
        binding.eTtitle.text = null

    }
}

