package com.lumnix.lumnixbrowser.OtherActivities

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lumnix.lumnixbrowser.R

class IntroSliderAdapter(private val introSlide: List<IntroSlide>):RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>(){

    inner class  IntroSliderViewHolder(view:View): RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDesc = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)

        fun bind(introSlide: IntroSlide){
            textTitle.text = introSlide.title
            textDesc.text = introSlide.description
            imageIcon.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
         return  IntroSliderViewHolder(
             LayoutInflater.from(parent.context).inflate(
                 R.layout.slide_item_container,
                 parent,
                 false
             )
         )
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }

    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.bind(introSlide[position])
    }
}