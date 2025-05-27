package com.example.apiRest.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.apiRest.R

class MovieAdapter(context: Context, movies: List<Movie>) : ArrayAdapter<Movie>(context, 0, movies) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder

        val view = if (convertView == null) {
            val newView = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_list_item, parent, false)
            viewHolder = ViewHolder()





            viewHolder.titleTextView = newView.findViewById(R.id.textOne)
            viewHolder.overviewTextView = newView.findViewById(R.id.text3)
            viewHolder.mainTitle = newView.findViewById(R.id.textTwo)
            viewHolder.DateRelease = newView.findViewById(R.id.text4)
            viewHolder.imageViewItems = newView.findViewById(R.id.myImageViewContent)
            newView.tag = viewHolder
            newView
        } else {
            viewHolder = convertView.tag as ViewHolder
            convertView
        }

        val movie = getItem(position)!!
        viewHolder.titleTextView.text = movie.title

      //
        viewHolder.mainTitle.text = movie.original_language
        viewHolder.overviewTextView.text = movie.overview
        viewHolder.DateRelease.text = movie.release_date

        viewHolder.titleTextView.setTextColor(Color.WHITE)
       // viewHolder.overviewTextView.setTextColor(Color.WHITE)
        viewHolder.mainTitle.setTextColor(Color.WHITE)
        viewHolder.DateRelease.setTextColor(Color.WHITE)


        val imageViewItems = view.findViewById<ImageView>(R.id.myImageViewContent)

        DownloadImageTask(imageViewItems).execute("https://image.tmdb.org/t/p/w500/${movie.poster_path}")

        return view
    }



    class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var mainTitle: TextView
        lateinit var imageViewItems: ImageView
        lateinit var DateRelease: TextView
        lateinit var overviewTextView: TextView
    }

    companion object {

    }
}