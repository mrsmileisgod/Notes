package the.mrsmile.notes

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import the.mrsmile.notes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var list: ArrayList<Items>
    private lateinit var databaseGet: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        list = arrayListOf()
        getData()
        setRecyclerView()

        //git button action
        binding.btnGit.setOnClickListener {
            openGit()
        }

        //tg btn action
        binding.btnTelegram.setOnClickListener {
            openTelegram()
        }

        binding.btnInsta.setOnClickListener {
            openInsta()
        }

        binding.fab.setOnClickListener {
            addNote()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun openGit() {
        val url = "https://github.com/mrsmileisgod"
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            startActivity(this)
        }
    }

    private fun openTelegram() {
        val url = "https://telegram.me/themrsmile"
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            startActivity(this)
        }
    }

    private fun openInsta() {
        val url = "https://www.instagram.com/theranjodhsingh/"
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
            startActivity(this)
        }
    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerMain
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.hasFixedSize()

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

        databaseGet.child("notes").get().addOnCompleteListener {
            binding.progressBar.visibility = View.GONE
            binding.tVProgressBar.visibility = View.GONE
        }

    }

    private fun addNote(){

        val intent = Intent(binding.root.context,MainActivity::class.java).apply {
            startActivity(this)
        }
    }

}


