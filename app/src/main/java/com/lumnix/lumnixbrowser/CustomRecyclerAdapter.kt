package com.lumnix.lumnixbrowser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_item.view.*

class CustomRecyclerAdapter(private var arrayImage:ArrayList<String>,
                            private var arrayHeading:ArrayList<String>,
                            private var arrayDesc:ArrayList<String>,
                            private var arrayLink:ArrayList<String>,
                            private val urlUpdaterCallBack: (url:String) -> Unit,
                            private val mContext:Context): RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayImage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(arrayImage[position])
            .centerCrop()
            .placeholder(R.drawable.background_home)
            .into(holder.image)
        holder.heading.text = arrayHeading[position]
        holder.desc.text = arrayDesc[position]

        holder.parent.setOnClickListener {
            urlUpdaterCallBack(arrayLink[position])
        }
    }



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image:ImageView = itemView.imageNews
        val heading:TextView = itemView.headingNews
        val desc:TextView = itemView.descNames
        val parent = itemView.parentNews!!
    }
}