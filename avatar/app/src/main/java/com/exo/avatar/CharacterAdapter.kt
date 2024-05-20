package com.exo.avatar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exo.avatar.connection.CharacterDto
import com.exo.avatar.databinding.ItemCharacterBinding
import com.squareup.picasso.Picasso

class CharacterAdapter(private val characters: List<CharacterDto>, private val onCharacterClicked: (CharacterDto) -> Unit ): RecyclerView.Adapter<CharacterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]

        holder.bind(character)

    /*    Glide.with(holder.itemView.context)
            .load(character.photoURL)
            .into(holder.ivCharacter)*/

        Picasso.get()
            .load(character.photoUrl)
            .into(holder.ivCharacter)

        holder.itemView.setOnClickListener{
            onCharacterClicked(character)
        }

    }

}