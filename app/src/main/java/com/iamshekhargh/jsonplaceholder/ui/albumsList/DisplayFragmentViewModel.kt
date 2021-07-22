package com.iamshekhargh.jsonplaceholder.ui.albumsList

import android.util.Log
import androidx.lifecycle.*
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumIdResponse
import com.iamshekhargh.jsonplaceholder.network.models.res.AlbumsResponse
import com.iamshekhargh.jsonplaceholder.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by <<-- iamShekharGH -->>
 * on 22 July 2021, Thursday
 * at 2:50 AM
 */
private const val TAG = "DisplayFragmentViewMode"

@HiltViewModel
class DisplayFragmentViewModel @Inject constructor(
    savedStateHandel: SavedStateHandle,
    private val repository: Repository
) :
    ViewModel() {
    private val events = Channel<Events>()
    val eventsFlow = events.receiveAsFlow()

    private val response = Channel<AlbumIdResponse>()
    val responseAsLiveData = response.receiveAsFlow().asLiveData()

    val tabList = savedStateHandel.get<AlbumsResponse>("tablist")

    val mapOfIds = hashMapOf<String, String>()

    fun getList(pageId: String) = viewModelScope.launch {
        val res = repository.getAlbumsItemList(pageId)
        response.send(res)
    }

    fun setAlbumId(id: String) = viewModelScope.launch {
//        currentPage.send(id)
        events.send(Events.PageChanged(id))
    }

    fun firstTimeEnteringFragment() = viewModelScope.launch {
        getList(tabList?.get(1)?.id.toString())
    }


}

sealed class Events {
    data class ShowSnackbar(val text: String) : Events()
    data class PageChanged(val text: String) : Events()
}