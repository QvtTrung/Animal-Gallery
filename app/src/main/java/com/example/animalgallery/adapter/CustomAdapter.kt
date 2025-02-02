package com.example.animalgallery.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.animalgallery.R
import com.example.animalgallery.activity.AnimalDetail
import com.example.animalgallery.model.AnimalModelAPI

class CustomAdapter(private val context: Context, private var animals: List<AnimalModelAPI>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val animal = animals[position]
        Glide.with(context)
            .load(animal.regular)
            .apply(
                RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
            .into(holder.image)

        // When you click the image
        holder.rlRoot.setOnClickListener {
            // when you click the image
            val intent = Intent(context, AnimalDetail::class.java)
            intent.putExtra("imageId",animal.id)
            intent.putExtra("imageUrl",animal.regular)
            intent.putExtra("imageTitle",animal.imageTitle)
            intent.putExtra("imageDescription",animal.imageDescription)
            intent.putExtra("imageDownloadLink", animal.imageDownloadLink)
            intent.putExtra("imageTag", animal.tag)
            context.startActivity(intent)
        }

        // When you click the share button
        holder.btnShare.setOnClickListener{
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain" // Set data type to plain text
                putExtra(Intent.EXTRA_TEXT, animal.imageDownloadLink) // Pass the image download link directly
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.animalImage)!!
        val rlRoot = itemView.findViewById<RelativeLayout>(R.id.rlroot)!!
        val btnShare = itemView.findViewById<ImageView>(R.id.btnShare)!!
    }

}