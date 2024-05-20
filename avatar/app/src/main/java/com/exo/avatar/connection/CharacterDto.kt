package com.exo.avatar.connection

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("_id")
    var id: String,
    //var allies: String lista?
    //var enemies: String
    var photoUrl: String,
    var name: String,
    var affiliation: String


)
