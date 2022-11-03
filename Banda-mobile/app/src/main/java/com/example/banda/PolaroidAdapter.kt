package com.example.banda

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.kakaologin.DogProfileData
import com.google.android.material.card.MaterialCardView

class PolariodAdapter(var datas: List<DogProfileData>) : RecyclerView.Adapter<PolariodAdapter.ViewHolder>() {
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
            bind(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frontAnimation = AnimatorInflater.loadAnimator(view.context, R.animator.flip_in) as AnimatorSet
        val backAnimation = AnimatorInflater.loadAnimator(view.context, R.animator.flip_out) as AnimatorSet

        fun bind(position: Int) {
            val frontCard = itemView.findViewById<MaterialCardView>(R.id.cardView)
            val backCard = itemView.findViewById<MaterialCardView>(R.id.cardViewBack)
            val scale = itemView.context.resources.displayMetrics.density
            frontCard.cameraDistance = 8000 * scale
            backCard.cameraDistance = 8000 * scale
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