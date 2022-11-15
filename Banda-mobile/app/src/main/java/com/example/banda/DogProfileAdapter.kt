package com.android.kakaologin

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banda.DogProfileData
import com.bumptech.glide.Glide
import com.example.banda.R
import java.text.SimpleDateFormat
import java.util.*

class DogProfileAdapter(var datas: List<DogProfileData>, val operation: (DogProfileData) -> Int, val dialog: Dialog?) : RecyclerView.Adapter<DogProfileAdapter.ViewHolder>() {

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
            bind(datas[position], operation, dialog)
        }
    }

    override fun getItemId(position: Int): Long {
        return datas[position].petId

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val dogName: TextView = view.findViewById(R.id.dog_name_textView)
        private val dogGender: TextView = view.findViewById(R.id.dog_gender_textView)
        private val dogBirth: TextView = view.findViewById(R.id.dog_birth_textView)
        private val dogBreed: TextView = view.findViewById(R.id.dog_breed_textView)
        private val dogImage: ImageView = view.findViewById(R.id.dog_image_imageView)
        private val dogDday : TextView = view.findViewById(R.id.dog_Dday_textView)
        var today = Calendar.getInstance()
        var format = SimpleDateFormat("yyyy-MM-dd")

        fun bind(item: DogProfileData, operation: (DogProfileData) -> Int, dialog: Dialog?) {
            itemView.setOnClickListener{
                operation(item)
                dialog?.dismiss()
            }
            Log.d("ASD", "here")
            dogName.text = item.name
            dogGender.text = if(item.gender == 0) "수컷" else "암컷"
            dogBirth.text = item.birth.toString()
            dogBreed.text = item.breed
            dogDday.text = ((today.time.time - format.parse(item.meetday).time) / (60 * 60 * 24 * 1000)).toString() + "일"
            if(item.img == ""){
                dogImage.setImageResource(R.drawable.pengun)
            } else {
                Glide.with(view).load(item.img).into(dogImage);
            }


        }
    }


}