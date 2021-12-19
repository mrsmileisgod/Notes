package the.mrsmile.notes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import the.mrsmile.notes.databinding.ActivityAddNoteBinding
import java.io.Serializable

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var item: Serializable? = null
    private var key: String? = null
    private val dao = DAONotes()


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        item = intent.getSerializableExtra("UPDATE")
        key = intent.getStringExtra("UPDATE_KEY")

        if (item != null && key != null) {
            val noteEdit = item as Notes
            binding.eTtitle.setText(noteEdit.title)
            binding.eTdesc.setText(noteEdit.desc)
        }

    }

    private fun updateItem() {
        val hashMap = HashMap<String, Any>()
        hashMap["title"] = binding.eTtitle.text.toString()
        hashMap["desc"] = binding.eTdesc.text.toString()
        key?.let {
            dao.update(it, hashMap)
        }
    }

    private fun writeData(title: String, desc: String) {

        val note = Notes(title, desc)
        val dao = DAONotes()
        dao.add(note)

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

        if (item != null) {
            updateItem()
        } else {
            if (title != "" && desc != "") {
                writeData(title, desc)
            }
        }

    }
}


