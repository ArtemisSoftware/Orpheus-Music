package com.artemissoftware.orpheusmusic.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.artemissoftware.orpheusmusic.R
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject

class SwipeSongAdapter : BaseSongAdapter(R.layout.list_item) {


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]

        holder.itemView.apply {

            val text = "${song.title} - ${song.subtitle}"
            tvPrimary.text = text

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)
}