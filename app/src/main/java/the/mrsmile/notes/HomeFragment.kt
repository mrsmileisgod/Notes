package the.mrsmile.notes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import the.mrsmile.notes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private lateinit var list: ArrayList<Notes>
    private val dao = DAONotes()
    private lateinit var adapter: RecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setRecyclerView()
        getData()


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
        layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()

    }

    private fun getData() {
        dao.get().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = ArrayList<Notes>()

                for (dataSnapshot in snapshot.children) {
                    val note = dataSnapshot.getValue(Notes::class.java)
                    note?.setKeys(dataSnapshot.key)
                    if (note != null) {
                        notes.add(note)
                    }
                }
                list = notes
                if(notes.isEmpty()){
                    binding.tVProgressBar.text = "Nothing to show :("
                    binding.progressBar.visibility = View.GONE
                    binding.fab.visibility = View.VISIBLE
                    Log.e("IFF",notes.toString())
                }
                else {
                    adapter = RecyclerAdapter()
                    adapter.setItems(notes)
                    recyclerView.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                    binding.tVProgressBar.visibility = View.GONE
                    binding.fab.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(binding.root,"Data fetching cancelled!",Snackbar.LENGTH_SHORT)
                    .show()
            }

        })

    }

    private fun addNote() {

        val intent = Intent(binding.root.context, AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun copyItem(position: Int) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Note", list[position].desc)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root, "Copied !", Snackbar.LENGTH_SHORT).show()
    }

    private fun deleteItem(position: Int) {
        val dao = DAONotes()

        val list = adapter.getItems()
        val currentItem = list[position]
        val key = currentItem.getKeys()
        Log.e("ActivityMain", key.toString())
        if (key != null) {
            dao.remove(key).addOnSuccessListener {
                getData()
                Snackbar.make(binding.root, "Deleted !", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateItem(position: Int){

        val listt = adapter.getItems()
        val currentItem = listt[position]
        val key = currentItem.getKeys()

        val item = list[position]
        val intent = Intent(activity,AddNoteActivity::class.java)
        intent.putExtra("UPDATE",item)
        intent.putExtra("UPDATE_KEY",key)
        startActivity(intent)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)

        when (item.itemId) {
            1-> {
                updateItem(item.groupId)
            }
            2 -> {
                copyItem(item.groupId)
                return true
            }
            3 -> {
                deleteItem(item.groupId)
                adapter.notifyItemRemoved(item.groupId)
                return true
            }
        }
        return true
    }
}



