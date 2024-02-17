package com.example.lab5_api

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.tracing.perfetto.handshake.protocol.Response
import org.chromium.base.Callback

class PokemonDetailViewModel : ViewModel() {
    private val _pokemonDetail: MutableLiveData<PokemonDetail> = MutableLiveData()
    val pokemonDetail: LiveData<PokemonDetail> = _pokemonDetail

    fun fetchPokemonDetail(pokemonId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(PokemonApi::class.java)
        val call: Call<PokemonDetail> = api.getPokemonDetail(pokemonId)

        call.enqueue(object : Callback<PokemonDetail?> {
            override fun onResponse(call: Call<PokemonDetail?>, response: Response<PokemonDetail?>) {
                if (response.isSuccessful) {
                    val detail = response.body()
                    if (detail != null) {
                        _pokemonDetail.postValue(detail)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonDetail?>, t: Throwable) {
                // Handle failure here
            }
        })
    }
}