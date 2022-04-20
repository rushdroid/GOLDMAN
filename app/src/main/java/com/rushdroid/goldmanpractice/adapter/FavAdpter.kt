package com.rushdroid.goldmanpractice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rushdroid.goldmanpractice.R
import com.rushdroid.goldmanpractice.model.NasaModel

class FavAdpter(
    val nasaFavList: List<NasaModel>,
    private val onClickListener: OnClickListener,
    private val onTextViewClick: OnTextViewClick
) :
    RecyclerView.Adapter<FavAdpter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = nasaFavList[position]
        // sets the text to the textview from our itemHolder class
        holder.textView.text =
            ItemsViewModel.date
        holder.img.setOnClickListener {
            onClickListener.onClick(ItemsViewModel)
        }
        holder.textView.setOnClickListener {
            onTextViewClick.onClick(ItemsViewModel)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return nasaFavList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.txtFavDate)
        val img: ImageView = itemView.findViewById(R.id.imgFavView)
    }
}

class OnClickListener(val clickListener: (nasaModel: NasaModel) -> Unit) {
    fun onClick(nasaModel: NasaModel) = clickListener(nasaModel)
}

class OnTextViewClick(val clickListener: (nasaModel: NasaModel) -> Unit) {
    fun onClick(nasaModel: NasaModel) = clickListener(nasaModel)
}