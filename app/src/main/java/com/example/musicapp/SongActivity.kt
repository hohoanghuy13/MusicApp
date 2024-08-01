package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SongActivity : AppCompatActivity() {
    private lateinit var edtSearchSong: EditText
    private lateinit var rvAllSong: RecyclerView
    private lateinit var bottomNavMain: BottomNavigationView
    private lateinit var songAdapter: SongAdapter

    //temp data
    private val songs: List<Song> = mutableListOf(
        Song(id = 0, title = "Pink Venom", artist = "Black Pink", duration = 187,
            picture = "https://firebasestorage.googleapis.com/v0/b/musicdatabase-6c059.appspot.com/o/imgpinkvenom.jpg?alt=media&token=346ff856-0b4f-45f9-b793-7753985e1f9e",
            songUrl = "https://firebasestorage.googleapis.com/v0/b/musicdatabase-6c059.appspot.com/o/pinkvenom_song.mp3?alt=media&token=e4eb7cc0-df0d-4dbd-83f6-f0c223f4eed4",
            preview = "https://firebasestorage.googleapis.com/v0/b/musicdatabase-6c059.appspot.com/o/pinkvenom_preview.mp3?alt=media&token=e0412947-8d84-40e2-a118-d15598f02290",
            releaseDate = 2022),
        Song(id = 1, title = "Shape Of You", artist = "Ed Sheeran", duration = 187, picture = "",
            songUrl = "https://firebasestorage.googleapis.com/v0/b/musicdatabase-6c059.appspot.com/o/shapeofyou.mp3?alt=media&token=d814d183-200c-414d-9916-854d4018673e",
            preview = "", releaseDate = 2022),
        Song(id = 2, title = "Dusk Still Dawn", artist = "Zayn", duration = 187, picture = "",
            songUrl = "https://firebasestorage.googleapis.com/v0/b/musicdatabase-6c059.appspot.com/o/duskstilldawn.mp3?alt=media&token=d65259d0-8837-4da5-b5fa-8eda8a2e9197",
            preview = "", releaseDate = 2022),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        addControls()
        prepareRecyclerView()
        addEvents()
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

    private fun addEvents() {
        songAdapter.setOnClickSongListener(
            object: SongAdapter.OnClickItemSong {
                override fun getItemData(song: Song) {
                    val intentSong = Intent(this@SongActivity, PlaySongActivity::class.java).apply {
                        putExtra(Constants.DATA_SONG_INTENT, song)
                    }
                    startActivity(intentSong)
                }
            }
        )
    }
}