package com.exo.avatar

import androidx.recyclerview.widget.RecyclerView
import com.exo.avatar.connection.CharacterDto
import com.exo.avatar.databinding.ItemCharacterBinding

class CharacterViewHolder(private val binding: ItemCharacterBinding):
    RecyclerView.ViewHolder(binding.root) {

        val ivCharacter = binding.ivCharacter
        fun bind(character: CharacterDto){
            binding.apply{
                tvCharacterName.text = character.name
                tvCharacterAffiliation.text = character.affiliation
            }
        }


}