package the.mrsmile.notes

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlin.collections.HashMap

class DAONotes {

    private var databaseReference: DatabaseReference

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Notes::class.java.simpleName)
    }

    fun add(note: Notes) : Task<Void>{
        return databaseReference.push().setValue(note)
    }

    fun update(key : String , hashMap: HashMap<String,Any>) : Task<Void>{
        return databaseReference.child(key).updateChildren(hashMap)
    }

    fun remove(key: String) : Task<Void>{
        return databaseReference.child(key).removeValue()
    }

    fun get() : Query {
        return databaseReference.orderByKey()
    }
}