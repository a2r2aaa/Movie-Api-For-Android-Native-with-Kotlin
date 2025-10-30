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

    val moviesall = mutableListOf<Movie>()

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
            val allMovies = mutableListOf<Movie>()
            val totalPages = 10

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    for (page in 1..totalPages) {
                        val request = Request.Builder()
                            .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=$page&api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
                            .build()

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
                                val movie = Movie(i, title, overview, pick, language, dateRealese)
                                allMovies.add(movie)
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        moviesall.clear()
                        moviesall.addAll(allMovies)

                        val filteredMovies = moviesall.filter { it.original_language == "es" }

                        val filteredMoviessecond = moviesall.filter { it.original_language == "en" }
                        adapter = MovieAdapter(this@MainActivity, ArrayList(filteredMovies))

                        adapter = MovieAdapter(this@MainActivity, ArrayList(filteredMoviessecond))
                        listView.adapter = adapter

                        for (movie in filteredMovies) {
                            val imageView = ImageView(this@MainActivity)

                            // Cargar imagen
                            loadImageIntoImageView(imageView, "https://image.tmdb.org/t/p/w500/${movie.poster_path}")

                            // Guardar el objeto Movie como tag en el ImageView
                            imageView.tag = movie

                            // Asignar evento onClick
                            imageView.setOnClickListener {
                                val clickedMovie = it.tag as Movie

                                val dialogImage = ImageView(this@MainActivity)
                                loadImageIntoImageView(dialogImage, "https://image.tmdb.org/t/p/w500/${clickedMovie.poster_path}")

                                AlertDialog.Builder(this@MainActivity)
                                    .setTitle(clickedMovie.title)
                                    .setCustomTitle(dialogImage)
                                    .setMessage("Language: ${clickedMovie.original_language}, " +
                                            "Date: ${clickedMovie.release_date}, " +
                                            "Overview: ${clickedMovie.overview}")
                                    .setPositiveButton("Entendido", null)
                                    .show()
                            }

                            findViewById<LinearLayout>(R.id.listViewContent).addView(imageView)
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
//println("kkkkkkkkkkkkkkkkkkkkk ${}")

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedMovie = adapter.getItem(position) as Movie

            val imageView = ImageView(this)
            loadImageIntoImageView(imageView, "https://image.tmdb.org/t/p/w500/${selectedMovie.poster_path}")

            AlertDialog.Builder(this)
                .setTitle(selectedMovie.title)
                .setCustomTitle(imageView)
                .setMessage("Language: ${ selectedMovie.original_language }, " +
                        "Date: ${ selectedMovie.release_date } ," +
                        "Overview: ${ selectedMovie.overview }")
                .setPositiveButton("Entendido", null)
                .show()
        }


        //listView22.onItemClickListener


      //  listView.adapter = adapter

      getMovies()


    }
}













