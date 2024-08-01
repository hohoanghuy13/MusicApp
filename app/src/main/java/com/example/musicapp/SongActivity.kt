package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView

class SongActivity : AppCompatActivity() {
    private lateinit var edtSearchSong: EditText
    private lateinit var rvAllSong: RecyclerView
    private lateinit var bottomNavMain: BottomNavigationView
    private lateinit var songAdapter: SongAdapter

    //temp data
    private val songs: List<Song> = mutableListOf(
        Song(id = 0, title = "Pink Venom", artist = "Black Pink", duration = 187, picture = "", songUrl = "", preview = "", releaseDate = 2022),
        Song(id = 1, title = "Shape Of You", artist = "Ed Sheeran", duration = 187, picture = "", songUrl = "", preview = "", releaseDate = 2022),
        Song(id = 2, title = "Dusk Still Dawn", artist = "Zayn", duration = 187, picture = "", songUrl = "", preview = "", releaseDate = 2022),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        addControls()
        prepareRecyclerView()
    }

    private fun addControls() {
        edtSearchSong = findViewById(R.id.edtSearchSong)
        rvAllSong = findViewById(R.id.rvAllSong)
        bottomNavMain = findViewById(R.id.bottomNavMain)
    }

    private fun prepareRecyclerView() {
        songAdapter = SongAdapter()
        songAdapter.submitList(songs)
        rvAllSong.adapter = songAdapter
        rvAllSong.layoutManager = GridLayoutManager(this, 1)
    }
}