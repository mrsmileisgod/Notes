package the.mrsmile.notes

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import the.mrsmile.notes.databinding.RecyclerItemsBinding

class RecyclerAdapter :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    var itemss = ArrayList<Notes>()

    class RecyclerViewHolder(itemsBinding: RecyclerItemsBinding) :
        RecyclerView.ViewHolder(itemsBinding.root), View.OnCreateContextMenuListener {

        var title = itemsBinding.tVTitle
        var desc = itemsBinding.tVDesc

        init {
            itemsBinding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

            menu?.add(bindingAdapterPosition, 1, 0, "Edit")
            menu?.add(bindingAdapterPosition, 2, 1, "Copy")
            menu?.add(bindingAdapterPosition, 3, 2, "Delete")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val currentItem = itemss[position]
        holder.title.text = currentItem.title.toString()
        holder.desc.text = currentItem.desc.toString()
    }


    override fun getItemCount(): Int {
        return itemss.size
    }

    fun setItems(items: ArrayList<Notes>) {
        Log.e(itemss.toString(), "setItems: ")
        itemss.addAll(items)
        Log.e(itemss.toString(), "setItems: ")

    }
    fun getItems() : ArrayList<Notes>{
        return itemss
    }

}