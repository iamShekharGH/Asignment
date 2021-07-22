package com.iamshekhargh.jsonplaceholder.network

import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumIdResponse
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 1:26 AM
 */
interface ApiInterface {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @Headers("Content-Type: application/json")
    @GET("albums/")
    suspend fun getAlbums(): AlbumsResponse

    @Headers("Content-Type: application/json")
    @GET("photos/")
    suspend fun getAlbumsIds(@Query("albumid") albumid: String): AlbumIdResponse


}