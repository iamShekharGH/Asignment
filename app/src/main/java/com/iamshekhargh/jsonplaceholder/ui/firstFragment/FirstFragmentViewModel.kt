package com.iamshekhargh.jsonplaceholder.ui.firstFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
 * at 1:23 AM
 */
@HiltViewModel
class FirstFragmentViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val event = Channel<Events>()
    val eventFlow = event.receiveAsFlow()

    private val response = Channel<AlbumsResponse>()
    private val responeFlow = response.receiveAsFlow()
    val responseAsLiveData = responeFlow.asLiveData()

    fun getAlbums() = viewModelScope.launch {
        response.send(repository.getAlbumsList())
    }

    fun dataArrived(data: AlbumsResponse?) = viewModelScope.launch {
        if (data == null) {
            event.send(Events.ShowSnackbar("List is empty."))
        } else {
            event.send(Events.OpenDisplayFragment(data))
        }
    }

}

sealed class Events {
    data class ShowSnackbar(val text: String) : Events()
    data class OpenDisplayFragment(val list: AlbumsResponse) : Events()
}