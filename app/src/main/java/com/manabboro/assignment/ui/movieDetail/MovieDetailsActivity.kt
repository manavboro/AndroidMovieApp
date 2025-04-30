package com.manabboro.assignment.ui.movieDetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.manabboro.assignment.R
import com.manabboro.assignment.databinding.ActivityMovieDetailsBinding
import com.manabboro.assignment.ui.movielist.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMovieDetailsBinding
    private val viewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        intent.getStringExtra("_id")?.let { viewModel.fetchMovieDetail(it) }

        lifecycleScope.launchWhenStarted {
            viewModel.movieDetail.collectLatest { movie ->
                movie?.let {
                    Glide.with(this@MovieDetailsActivity)
                        .load(movie.Poster)  // From your JSON
                        .into(mBinding.posterImage)

                    mBinding.titleText.text = movie.Title
                    mBinding.releaseAndRating.text = "${movie.Released} • IMDb ${movie.imdbRating}"
                    mBinding.genreText.text = movie.Genre
                    mBinding.plotText.text = movie.Plot
                }
            }
        }
    }
}