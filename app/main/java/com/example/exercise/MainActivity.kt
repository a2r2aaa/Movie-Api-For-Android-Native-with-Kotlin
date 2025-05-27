package com.example.apiRest

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.example.apiRest.ui.main.*
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
            AsyncTask.execute {
                val url = URL(imageUrl)
                val inputStream = url.openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                runOnUiThread {
                    imageView.setImageBitmap(bitmap)
                }
            }
        }

         fun getMovies() {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=undefined&api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
                .build()

            AsyncTask.execute {
                client.newCall(request).execute().use { response ->
                    val json = JSONObject(response.body()!!.string())
                    val results = json.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val movieJson = results.getJSONObject(i)
                        val title = movieJson.getString("title")
                        val overview = movieJson.getString("overview")
                        val pick = movieJson.getString("poster_path")
                        val language = movieJson.getString("original_language")
                        val dateRealese = movieJson.getString("release_date")
                        val movie = Movie(888, title, overview, pick, language,dateRealese)

                        runOnUiThread { adapter.add(movie) }
                    }
                }
            }

                 AsyncTask.execute {
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
                         val listView2 = findViewById<LinearLayout>(R.id.listViewContent)
                         for (movie in movies) {
                             val imageView = ImageView(this)
                             loadImageIntoImageView(imageView, "https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                             runOnUiThread {
                                 listView2.addView(imageView)
                             }
                         }
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
            DownloadImageTask(imageView).execute("https://image.tmdb.org/t/p/w500/${selectedMovie.poster_path}")

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