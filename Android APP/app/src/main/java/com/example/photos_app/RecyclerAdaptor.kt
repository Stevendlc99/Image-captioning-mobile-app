package com.example.photos_app

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RecyclerAdaptor(
    private val list: ArrayList<*>,
    private val context: Context,
    var countOfImagesWhenRemoved: CountOfImagesWhenRemoved
) :
    RecyclerView.Adapter<RecyclerAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_item_layout, parent, false)
        return ViewHolder(view, countOfImagesWhenRemoved)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //holder.imageView.setImageURI((Uri) list.get(position));
        holder.imageView_del.setOnClickListener {
            list.remove(list[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            countOfImagesWhenRemoved.clicked(list.size)
        }

        Glide.with(context)
            .load(list[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View, var countOfImagesWhenRemoved: CountOfImagesWhenRemoved) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var imageView_del: ImageView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            imageView_del = itemView.findViewById(R.id.imageView_del)
        }
    }

    interface CountOfImagesWhenRemoved {
        fun clicked(getSize: Int)
    }
}