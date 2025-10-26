package com.example.exercise

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.widget.*
import com.example.apiRest.R
import com.example.apiRest.ui.main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var listView22: ListView
    private lateinit var listView2: LinearLayout
    private lateinit var adapter: MovieAdapter
    private lateinit var adapter2: ItemImageAdapter

    private lateinit var imageViewExample: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        fun loadImageIntoImageView(imageView: ImageView, imageUrl: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL(imageUrl)
                    val inputStream = url.openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        fun getMovies() {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1&api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
                .build()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    client.newCall(request).execute().use { response ->
                        val json = JSONObject(response.body()!!.string())
                        val results = json.getJSONArray("results")
                        val movies = mutableListOf<Movie>()
                        for (i in 0 until results.length()) {
                            val movieJson = results.getJSONObject(i)
                            val title = movieJson.getString("title")
                            val overview = movieJson.getString("overview")
                            val pick = movieJson.getString("poster_path")
                            val language = movieJson.getString("original_language")
                            val dateRealese = movieJson.getString("release_date")
                            val movie = Movie(i, title, overview, pick, language, dateRealese)
                            movies.add(movie)
                        }

                        withContext(Dispatchers.Main) {
                            for (movie in movies) {
                                adapter.add(movie)
                                val imageView = ImageView(this@MainActivity)
                                loadImageIntoImageView(imageView, "https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                                findViewById<LinearLayout>(R.id.listViewContent).addView(imageView)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       listView = findViewById(R.id.listView)
        listView.dividerHeight = 9
        adapter = MovieAdapter(this, ArrayList())

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedMovie = adapter.getItem(position) as Movie

            val imageView = ImageView(this)
            loadImageIntoImageView(imageView, "https://image.tmdb.org/t/p/w500/${selectedMovie.poster_path}")

            AlertDialog.Builder(this)
                .setTitle(selectedMovie.title)
                .setCustomTitle(imageView)
                .setMessage("Language: ${ selectedMovie.original_language }, Date: ${ selectedMovie.release_date } ,Overview: ${ selectedMovie.overview }")
                .setPositiveButton("Entendido", null)
                .show()
        }


        listView.adapter = adapter

      getMovies()

        val apiKey = "55957fcf3ba81b137f8fc01ac5a31fb5"
        val call = ApiClient.tmdbService.getNowPlaying(apiKey, "en-US", 1)
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movies = response.body()?.results

                if (movies != null) {
                    println("movies"+movies)
                }
                // Use the movies list
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                // Handle error
            }

        })
    }
}













