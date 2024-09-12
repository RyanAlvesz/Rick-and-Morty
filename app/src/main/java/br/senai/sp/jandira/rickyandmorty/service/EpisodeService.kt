package br.senai.sp.jandira.rickyandmorty.service

import br.senai.sp.jandira.rickyandmorty.model.EpisodesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeService {

    @GET("episode/{ids}/")
    fun getEpisodesByCharacter (@Path("ids") id: String) : Call<EpisodesList>

}