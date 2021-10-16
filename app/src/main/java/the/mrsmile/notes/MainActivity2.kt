package the.mrsmile.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import the.mrsmile.notes.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMain2Binding
    private lateinit var list: ArrayList<Items>
    private lateinit var databaseGet: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        currentFragment(HomeFragment())
        binding.bottomNavBar.setItemSelected(R.id.menu_home, true)
        binding.bottomNavBar.setOnItemSelectedListener {
            when(binding.bottomNavBar.getSelectedItemId()) {
                R.id.menu_home -> { supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, HomeFragment()).commit()
                    
            }
                R.id.menu_search -> supportFragmentManager.beginTransaction().replace(R.id.fragment,SearchFragment()).commit()
                R.id.menu_feedback -> supportFragmentManager.beginTransaction().replace(R.id.fragment,FeedbackFragment()).commit()

            }
        }
//
//        list = arrayListOf()
//        getData()
//
//
//        recyclerView = binding.recyclerMain
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.hasFixedSize()
    }

    private fun currentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,fragment).commit()
        }
    }

//    private fun getData() {
//
//        databaseGet = FirebaseDatabase.getInstance().getReference("notes")
//        databaseGet.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()) {
//                    for (noteSnapshot in snapshot.children) {
//
//                        val note = noteSnapshot.getValue<Items>()
//                        Log.d("ListData", noteSnapshot.toString())
//                        if (!list.contains(note)) {
//                            list.add(0, note!!)
//                        }
//                    }
//                    recyclerView.adapter = RecyclerAdapter(list)
//                }
//            }
//
//
//            override fun onCancelled(error: DatabaseError) {
//                Snackbar.make(binding.root, "Data Fetching Cancelled !", Snackbar.LENGTH_SHORT)
//                    .show()
//            }
//        })
//
//////        if(databaseGet.)
//        databaseGet.child("notes").get().addOnCompleteListener {
//            binding.progressBar.visibility = View.GONE
//            binding.tVProgressBar.visibility = View.GONE
//        }
//
//    }
}