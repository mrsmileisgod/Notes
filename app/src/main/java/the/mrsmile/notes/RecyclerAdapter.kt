package the.mrsmile.notes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import the.mrsmile.notes.databinding.RecyclerItemsBinding

class RecyclerAdapter(private val items : List<Items>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemsBinding: RecyclerItemsBinding) : RecyclerView.ViewHolder(itemsBinding.root){

        var title = itemsBinding.tVTitle
        var desc = itemsBinding.tVDesc
        var btn = itemsBinding.btnCopy
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    val currentItem = items[position]
        holder.title.text = currentItem.title.toString()
        holder.desc.text = currentItem.desc.toString()


        holder.btn.setOnClickListener {
            val clipboard = it.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("Note",holder.desc.text)
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it,"Copied !",Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }
}