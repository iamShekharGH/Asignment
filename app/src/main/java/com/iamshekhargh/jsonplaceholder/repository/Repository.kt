package com.iamshekhargh.jsonplaceholder.repository

import android.util.Log
import com.iamshekhargh.jsonplaceholder.network.ApiInterface
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumIdResponse
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumsResponse
import com.iamshekhargh.jsonplaceholder.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 1:25 AM
 */
private const val TAG = "Repository"

class Repository @Inject constructor(
    private val api: ApiInterface
) {

    suspend fun getAlbumsList(): AlbumsResponse {
        val response = api.getAlbums()
        return response
    }

    suspend fun getAlbumsItemList(albumId: String): AlbumIdResponse {
        val response = api.getAlbumsIds(albumId)
        return response
    }


}
