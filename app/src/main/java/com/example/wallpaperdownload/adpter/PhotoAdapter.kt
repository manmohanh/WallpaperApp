package com.example.wallpaperdownload.adpter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperdownload.databinding.PhotoItemBinding
import com.example.wallpaperdownload.model.Photo

class PhotoAdapter(
    private val context: Context,
    private val photos: List<Photo>,
    private val onClicked: (photoUri: String) -> Unit
):RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    class PhotoViewHolder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root){
        val photo:ImageView = binding.photoImg
        val options:ImageButton = binding.optionButton
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Glide.with(context).load(photos[position].photoUrl).into(holder.photo)
        holder.options.setOnClickListener {
            photos[position].photoUrl?.let { it1 -> onClicked(it1) }
        }
    }
}