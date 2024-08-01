package com.example.musicapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SongAdapter : ListAdapter<Song, SongAdapter.SongViewHolder>(SongComparator()) {
    private lateinit var onClickItem: OnClickItemSong
    fun setOnClickSongListener(onClickItemSong: OnClickItemSong) {
        onClickItem = onClickItemSong
    }
    class SongViewHolder(itemView: View) : ViewHolder(itemView) {
        private val tvNameSong: TextView = itemView.findViewById(R.id.tvNameSong)
        private val tvNameArtists: TextView = itemView.findViewById(R.id.tvNameArtists)
        private val imgItemSong: ImageView = itemView.findViewById(R.id.imgItemSong)
        private val imgBtnActionItem: ImageButton = itemView.findViewById(R.id.imgBtnActionItem)

        fun bind(song: Song) {
            tvNameSong.text = song.title
            tvNameArtists.text = song.artist
            imgItemSong.setImageResource(R.drawable.baseline_music_note_24)

            imgBtnActionItem.setOnClickListener {
                //more action
                Log.d("Button Click", "Button Item Song Clicked")
            }
        }
    }

    class SongComparator() : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean = oldItem === newItem

        //continue write equals() object
        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title
        }
    }

    interface OnClickItemSong {
        fun getItemData(song: Song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = getItem(position)
        holder.bind(song)

        holder.itemView.setOnClickListener {
            onClickItem.getItemData(song)
        }
    }
}