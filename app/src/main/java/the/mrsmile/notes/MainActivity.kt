package the.mrsmile.notes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import the.mrsmile.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.bottomNavView.menu.getItem(0).isCheckable = false
        binding.bottomNavView.menu.getItem(1).isCheckable = false
        binding.bottomNavView.menu.getItem(2).isCheckable = false
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navViewSave -> {
                    val title = getTitlee()
                    val desc = getDesc()
                    if (title != "" && desc != "") {
                        writeData(title, desc)
                        Snackbar.make(binding.root, "Note Added !", Snackbar.LENGTH_SHORT).show()

                    } else {
                        Snackbar.make(this, binding.root, "Invalid Note Provided !", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    binding.eTdesc.text = null
                    binding.eTtitle.text = null
                }

//                R.id.navViewUndo -> Snackbar.make(binding.root,"Undo",Snackbar.LENGTH_SHORT).show()
//                R.id.navViewRedo -> Snackbar.make(binding.root,"Redo",Snackbar.LENGTH_SHORT).show()
            }
            true
        }

    }

    private fun writeData(title: String, desc: String) {
        val note = Items(title, desc)
        database.child("notes").child(title).setValue(note)
    }

    private fun getTitlee(): String {
        return binding.eTtitle.text.toString()
    }

    private fun getDesc(): String {
        return binding.eTdesc.text.toString()
    }


}

