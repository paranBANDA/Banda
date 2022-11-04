package com.example.banda

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banda.polaroid.PolaroidData
import com.google.android.material.card.MaterialCardView

class PolariodAdapter(var datas: List<PolaroidData>) : RecyclerView.Adapter<PolariodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.polaroid,parent,false)
        //flipArray  = MutableList<Boolean>(datas.size) { false }
        Log.d("bbbb", datas.size.toString())
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(datas[position])
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val frontAnimation = AnimatorInflater.loadAnimator(view.context, R.animator.flip_in) as AnimatorSet
        val backAnimation = AnimatorInflater.loadAnimator(view.context, R.animator.flip_out) as AnimatorSet

        fun bind(data: PolaroidData) {
            val frontCard = itemView.findViewById<MaterialCardView>(R.id.cardView)
            val backCard = itemView.findViewById<MaterialCardView>(R.id.cardViewBack)
            val imageViewFront = itemView.findViewById<ImageView>(R.id.imageViewFront)
            val imageViewBack = itemView.findViewById<ImageView>(R.id.imageViewBack)
            val textViewFront = itemView.findViewById<TextView>(R.id.textViewFront)
            val textViewBack = itemView.findViewById<TextView>(R.id.textViewBack)


            val scale = itemView.context.resources.displayMetrics.density
            frontCard.cameraDistance = 8000 * scale
            backCard.cameraDistance = 8000 * scale

            Glide.with(view).load(data.dogDiaryImageUrl).error(R.drawable.pengun).into(imageViewFront);
            Glide.with(view).load(data.masterDiaryImageUrl).error(R.drawable.pengun).into(imageViewBack);

            textViewBack.text = data.masterDiaryText
            textViewFront.text = data.dogDiaryText


            frontCard.setOnClickListener {
                frontAnimation.setTarget(backCard)
                backAnimation.setTarget(frontCard)
                frontAnimation.start()
                backAnimation.start()
                frontCard.isClickable = false
                backCard.isClickable = true

            }

            backCard.setOnClickListener {
                frontAnimation.setTarget(frontCard)
                backAnimation.setTarget(backCard)
                frontAnimation.start()
                backAnimation.start()
                backCard.isClickable = false
                frontCard.isClickable = true

            }

            Log.d("ASD", "here")
        }
    }


}