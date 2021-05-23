package com.artemissoftware.orpheusmusic.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.artemissoftware.orpheusmusic.R
import kotlinx.android.synthetic.main.list_item.view.*

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) { //need to be on a module object because it does not have @Inject to tell dagger to automatically create it


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