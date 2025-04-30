package com.manabboro.assignment.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manabboro.assignment.MovieLoadStateAdapter
import com.manabboro.assignment.R
import com.manabboro.assignment.model.MovieDetail
import com.manabboro.assignment.ui.movieDetail.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {


    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        movieAdapter = MovieAdapter { movie ->
            run {
                val intent = Intent(this, MovieDetailsActivity::class.java)
                intent.putExtra("_id", movie.imdbID)
                startActivity(intent)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )

        lifecycleScope.launchWhenStarted {
            viewModel.movies.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}