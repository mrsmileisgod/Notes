package the.mrsmile.notes

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Notes(var title: String? = null, var desc : String? = null , @get:Exclude var key : String? = null ) : Serializable {

    fun getKeys() : String? {
        return key
    }
    fun setKeys(key : String?) {
        this.key = key
    }
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}