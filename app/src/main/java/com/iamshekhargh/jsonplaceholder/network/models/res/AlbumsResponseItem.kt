package com.iamshekhargh.jsonplaceholder.network.models.res

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumsResponseItem(
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable