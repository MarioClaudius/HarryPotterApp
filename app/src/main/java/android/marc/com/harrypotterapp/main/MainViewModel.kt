package android.marc.com.harrypotterapp.main

import android.marc.com.core.data.source.remote.api.ApiConfig
import android.marc.com.core.domain.model.Character
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> = _characterList

    init {
        displayCharacterList()
    }

    fun displayCharacterList() {
        _isLoading.value = true
        val client = ApiConfig().getApiService().getAllCharacters()
        client.enqueue(object : Callback<List<Character>> {
            override fun onResponse(
                call: Call<List<Character>>,
                response: Response<List<Character>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _characterList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}