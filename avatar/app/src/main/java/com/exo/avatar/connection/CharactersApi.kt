package com.exo.avatar.connection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface CharactersApi {

    // https://last-airbender-api.fly.dev/api/v1/characters?perPage=497
    @GET
    fun getCharacters(
        @Url url: String
    ): Call<List<CharacterDto>>
    //getCharacters("api/v1/characters?perPage=497")

    //https://last-airbender-api.fly.dev/api/v1/characters/5cf5679a915ecad153ab68cb
    @GET("api/v1/characters/{id}")
    fun getCharacterDetail(
        @Path("id")id: String
    ): Call<CharacterAdvDto>
    //getCharacterDetail("a3345c3")

}