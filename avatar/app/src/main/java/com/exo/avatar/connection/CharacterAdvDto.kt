package com.exo.avatar.connection

import com.google.gson.annotations.SerializedName

data class CharacterAdvDto(
    @SerializedName("_id")
    var id: String,
    var photoUrl: String,
    var name: String,
    var affiliation: String,
    var gender: String,
    var profession: String,
    var position: String,
    var weapon: String
)
