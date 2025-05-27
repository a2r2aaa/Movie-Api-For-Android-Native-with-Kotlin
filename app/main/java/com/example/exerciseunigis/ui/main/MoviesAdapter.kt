package com.example.apiRest.ui.main

//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.apiRest.R
//import kotlinx.android.synthetic.main.item_layout.view.*
//
//class MoviesAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val movie = movies[position]
//        holder.bind(movie)
//    }
//
//    override fun getItemCount() = movies.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(movie: Movie) {
//            itemView.titleTextView.text = movie.title
//            itemView.overviewTextView.text = movie.overview
//        }
//    }
//}