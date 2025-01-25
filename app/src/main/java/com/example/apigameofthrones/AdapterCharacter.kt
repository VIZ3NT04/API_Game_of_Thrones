package com.example.apigameofthrones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apigameofthrones.databinding.ItemCharactersBinding
import com.squareup.picasso.Picasso

class AdapterCharacter(private val characters: List<Character>) : RecyclerView.Adapter<AdapterCharacter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCharactersBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_characters, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]

        with(holder) {
            binding.tvName.text = character.name
            binding.tvCulture.text = character.culture
            binding.tvDied.text = if (character.died.isEmpty()) "No died" else character.died
            binding.tvTitles.text = if (character.titles.isEmpty()) "[No title]" else character.titles.toString()


        }
    }

    override fun getItemCount(): Int = characters.size
}