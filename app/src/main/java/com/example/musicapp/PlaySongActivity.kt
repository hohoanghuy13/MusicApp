package com.example.musicapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class PlaySongActivity : AppCompatActivity() {
    private lateinit var tvSong: TextView
    private lateinit var tvArtists: TextView
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var seekBarPlaySong: SeekBar
    private lateinit var imgBtnPlay: ImageButton
    private lateinit var mediaPlayer: MediaPlayer
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        setSupportActionBar(findViewById(R.id.toolbarPlaySong))

        val song = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.DATA_SONG_INTENT, Song::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Song>(Constants.DATA_SONG_INTENT)
        }

        addControls()

        if (song?.songUrl != null) {
            tvSong.text = song.title
            tvArtists.text = song.artist

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(song.songUrl)
                prepare()
                start()
            }

            seekBarPlaySong.max = mediaPlayer.duration
            seekBarPlaySong.progress = mediaPlayer.currentPosition
            tvEndTime.text = formatTime(mediaPlayer.duration)

            timer.scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    runOnUiThread {
                        seekBarPlaySong.progress = mediaPlayer.currentPosition

                        if(seekBarPlaySong.max == seekBarPlaySong.progress) {
                            seekBarPlaySong.progress = 0
                            mediaPlayer.seekTo(0)
                            imgBtnPlay.setImageResource(R.drawable.baseline_play_circle_filled_24)
                        }

                        tvStartTime.text = formatTime(mediaPlayer.currentPosition)
                    }
                }
            }, 0, 1000)

            imgBtnPlay.setImageResource(R.drawable.baseline_pause_circle_filled_24)

            imgBtnPlay.setOnClickListener {
                //Play song
                if(mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    imgBtnPlay.setImageResource(R.drawable.baseline_play_circle_filled_24)
                } else {
                    mediaPlayer.start()
                    imgBtnPlay.setImageResource(R.drawable.baseline_pause_circle_filled_24)
                }
                Log.d("Test", mediaPlayer.currentPosition.toString() + " - "
                        + mediaPlayer.duration.toString())
            }

            seekBarPlaySong.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(p2) {
                        mediaPlayer.seekTo(p1)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })
        }


//        mediaPlayer.seekTo(0)
    }



    private fun addControls() {
        tvSong = findViewById(R.id.tvSong)
        tvArtists = findViewById(R.id.tvArtists)
        tvStartTime = findViewById(R.id.tvStartTime)
        tvEndTime = findViewById(R.id.tvEndTime)
        seekBarPlaySong = findViewById(R.id.seekBarPlaySong)
        imgBtnPlay = findViewById(R.id.imgBtnPlay)
    }


    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
        timer.cancel()
    }

    private fun formatTime(milliseconds: Int) : String {
        val minute = (milliseconds / 1000) / 60
        val second = (milliseconds / 1000) % 60
        return String.format("%02d:%02d", minute, second)
    }
}