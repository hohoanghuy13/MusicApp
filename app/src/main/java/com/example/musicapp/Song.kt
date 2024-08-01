package com.example.musicapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Song (
    val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val picture: String,
    val songUrl: String,
    val preview: String,
    val releaseDate: Int
) : Parcelable {

}