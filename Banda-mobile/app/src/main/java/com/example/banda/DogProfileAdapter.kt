package com.android.kakaologin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.kakaologin.DogProfileData
import com.example.banda.R

class DogProfileAdapter(var datas: List<DogProfileData>) : RecyclerView.Adapter<DogProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_profile_item,parent,false)
        return DogProfileAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("ASD", datas.size.toString())
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(datas[position])
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dogName: TextView = view.findViewById(R.id.dog_name_textView)
        private val dogGender: TextView = view.findViewById(R.id.dog_gender_textView)
        private val dogBirth: TextView = view.findViewById(R.id.dog_birth_textView)
        private val dogBreed: TextView = view.findViewById(R.id.dog_breed_textView)
        private val dogImage: ImageView = view.findViewById(R.id.dog_image_imageView)

        fun bind(item: DogProfileData) {
            Log.d("ASD", "here")
            dogName.text = item.name
            dogGender.text = if(item.gender == 0) "수컷" else "암컷"
            dogBirth.text = item.birth
            dogBreed.text = item.breed
            dogImage.setImageResource(R.drawable.pengun)
        }
    }


}