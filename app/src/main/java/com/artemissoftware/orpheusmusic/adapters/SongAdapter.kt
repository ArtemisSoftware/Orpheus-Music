package com.artemissoftware.orpheusmusic.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artemissoftware.orpheusmusic.R
import com.artemissoftware.orpheusmusic.data.entities.Song
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject

class SongAdapter @Inject constructor( //does not need to be on a module object because @Inject tell dagger to automatically do that
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.list_item) {


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]

        holder.itemView.apply {
            tvPrimary.text = song.title
            tvSecondary.text = song.subtitle
            glide.load(song.imageUrl).into(ivItemImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)
}