package com.example.apiRest.ui.main

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.apiRest.R
import java.net.URL

class ItemImageAdapter(context: Context, movies: List<Movie>) : ArrayAdapter<Movie>(context, 0, movies) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder


        val view = if (convertView == null) {
            val newView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
            viewHolder = ViewHolder()
            viewHolder.titleTextView = newView.findViewById(android.R.id.text1)
            viewHolder.overviewTextView = newView.findViewById(android.R.id.text2)
            newView.tag = viewHolder
            newView
        } else {
            viewHolder = convertView.tag as ViewHolder
            convertView
        }

        val movie = getItem(position)!!
        viewHolder.titleTextView.text = movie.title
        viewHolder.overviewTextView.text = movie.overview







//        fun getDrawableFromName(context: Context, drawableName: String): Drawable {
//
//
//
//            val resId = context.resources.getIdentifier(drawableName, drawableName, context.packageName)
//
//            return context.resources.getDrawable(R.drawable.ic_launcher_background)
//        }
//
//
//
//
//        val imageView = viewItem.findViewById<ImageView>(R.id.imageViewItem)
//
//
//        viewHolder.imageViewExample = imageView
//        val drawableName = "movie.poster_path"
//        val context = view.context
//        // val drawable = getDrawableFromName(context, "movie.poster_path")
//
//        viewHolder.imageViewExample.setImageDrawable(getDrawableFromName(this.context, drawableName))

        return view
    }



    class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var overviewTextView: TextView
        lateinit var imageViewExample: ImageView
    }

}