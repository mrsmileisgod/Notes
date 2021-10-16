package the.mrsmile.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import the.mrsmile.notes.databinding.FragmentAddNoteBinding

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private var index = 0
    private lateinit var database: DatabaseReference
    private lateinit var databaseGet: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)


        index = getIndex()
        binding.btnAddNote.setOnClickListener {

            val title = getTitlee()
            val desc = getDesc()
            if (title != "" && desc != "") {
                writeData(title, desc)
                Snackbar.make(binding.constraintLayout, "Note Added !", Snackbar.LENGTH_SHORT)
                    .show()

            } else {
                Snackbar.make(
                    binding.root.context,
                    binding.root,
                    "Invalid Note Provided !",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
            binding.eTdesc.text = null
            binding.eTtitle.text = null

        }
        return binding.root
    }


    private fun writeData(title: String, desc: String) {
        val note = Items(title, desc)
        Log.e("INDEX MAIN ACTIVITY", index.toString())
        database.child("notes").child(index.toString()).setValue(note)
        index++

    }

    private fun getTitlee(): String {
        return binding.eTtitle.text.toString()
    }

    private fun getDesc(): String {
        return binding.eTdesc.text.toString()
    }

    private fun getIndex(): Int {
        databaseGet = FirebaseDatabase.getInstance().getReference("notes")
        databaseGet.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    index = snapshot.childrenCount.toInt()
                    Log.e("get index", index.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(binding.root,"Data Fetching Cancelled!",Snackbar.LENGTH_SHORT).show()
            }

        })
        return index

    }
}