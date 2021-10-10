package the.mrsmile.notes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Items(var title: String? = null, var desc : String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}