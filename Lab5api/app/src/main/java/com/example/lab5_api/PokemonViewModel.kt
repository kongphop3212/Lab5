package com.example.lab5_api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PokemonViewModel : ViewModel() {
    private val _data: MutableLiveData<List<Pokemon>> = MutableLiveData()
    val pokemonList: LiveData<List<Pokemon>> = _data

    init {
        fetchDataFromAPI()
    }

    private fun fetchDataFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(PokemonApi::class.java)
        val call: Call<PokemonList> = api.getPokemonList()

        call.enqueue(object: Callback<PokemonList?> {
            override fun onResponse(call: Call<PokemonList?>, response: Response<PokemonList?>) {
                if(response.isSuccessful) {
                    Log.d("PokemonViewModel", "success!" + response.body().toString())
                    val responseList = response.body()?.results ?: emptyList()
                    _data.postValue(responseList)
                }
            }
            override fun onFailure(call: Call<PokemonList?>, t: Throwable) {
                Log.e("PokemonViewModel", "Failed mate " + t.message.toString())
            }
        })
    }
}