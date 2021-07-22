package com.iamshekhargh.jsonplaceholder.ui.albumsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.iamshekhargh.jsonplaceholder.R
import com.iamshekhargh.jsonplaceholder.databinding.ItemAblumsBinding
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumIdResponseItem

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 2:53 AM
 */
class AlbumItemAdapter :
    ListAdapter<AlbumIdResponseItem, AlbumItemAdapter.AlbumsViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val binding = ItemAblumsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlbumsViewHolder(private val binding: ItemAblumsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlbumIdResponseItem) {
            binding.apply {

                val url = GlideUrl(
                    item.thumbnailUrl,
                    LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build()
                )

                Glide.with(itemAblumsImageview).load(url).centerCrop()
                    .placeholder(R.drawable.placeholder).dontAnimate().into(itemAblumsImageview)
                itemAblumsTextview.text = item.title
            }
        }
    }
}

class DiffUtilItemCallback : DiffUtil.ItemCallback<AlbumIdResponseItem>() {
    override fun areItemsTheSame(
        oldItem: AlbumIdResponseItem,
        newItem: AlbumIdResponseItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AlbumIdResponseItem,
        newItem: AlbumIdResponseItem
    ): Boolean {
        return oldItem == newItem
    }

}