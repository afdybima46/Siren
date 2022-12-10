package com.example.siren.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var id: String? = null,
    var title: String? = null,
    var author: String? = null,
    var image: String? = null,
    var caption: String? = null,
    var tag: String? = null,
    var content: String? = null
) : Parcelable
