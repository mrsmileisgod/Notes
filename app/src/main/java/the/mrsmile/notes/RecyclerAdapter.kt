package the.mrsmile.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.mrsmile.notes.databinding.RecyclerItemsBinding

class RecyclerAdapter(private val items : List<Items> , private val listener : LongClick) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    inner class RecyclerViewHolder(itemsBinding: RecyclerItemsBinding) : RecyclerView.ViewHolder(itemsBinding.root) , View.OnLongClickListener{

        var title = itemsBinding.tVTitle
        var desc = itemsBinding.tVDesc

        override fun onLongClick(v: View?): Boolean {
            listener.copyItem(bindingAdapterPosition)
            return true
        }
        init {
            itemsBinding.root.setOnLongClickListener(this)
        }
    }

    interface LongClick{
        fun copyItem(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    val currentItem = items[position]
        holder.title.text = currentItem.title.toString()
        holder.desc.text = currentItem.desc.toString()
    }


    override fun getItemCount(): Int {
        return items.size
    }
}