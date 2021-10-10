package the.mrsmile.notes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import the.mrsmile.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var databaseGet: DatabaseReference
    lateinit var recyclerView: RecyclerView
    private lateinit var list: ArrayList<Items>

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        database = Firebase.database.reference
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list = arrayListOf()
        getData()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
        binding.btnLongNote.setOnClickListener {

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
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.primary))
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

    private fun getData() {

        databaseGet = FirebaseDatabase.getInstance().getReference("notes")
        databaseGet.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (noteSnapshot in snapshot.children) {

                        val note = noteSnapshot.getValue<Items>()
                        Log.d("ListData", noteSnapshot.toString())
                        if (!list.contains(note)) {
                            list.add(0, note!!)
                        }
                    }
                    recyclerView.adapter = RecyclerAdapter(list)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(binding.root, "Data Fetching Cancelled !", Snackbar.LENGTH_SHORT)
                    .show()
            }
        })

//        if(databaseGet.)
        databaseGet.child("notes").child(getTitlee()).get().addOnSuccessListener {
            binding.progressBar.visibility = View.GONE
            binding.tVProgressBar.visibility = View.GONE
        }

    }

}