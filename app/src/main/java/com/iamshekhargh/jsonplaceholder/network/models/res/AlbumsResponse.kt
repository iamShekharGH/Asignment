package com.iamshekhargh.jsonplaceholder.network.models.res

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AlbumsResponse : ArrayList<AlbumsResponseItem>(), Parcelable