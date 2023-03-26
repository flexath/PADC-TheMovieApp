package com.flexath.themovieapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.flexath.themovieapp.R
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.utils.IMAGE_BASE_URL
import com.flexath.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    // ViewPods
    private lateinit var actorsViewPod: ActorListViewPod
    private lateinit var creatorsViewPod: ActorListViewPod

    private val mMovieModel: MovieModel = MovieModelImpl

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setUpViewPods()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let {
            requestData(it)
        }
    }

    private fun requestData(movieId: Int) {
        mMovieModel.getMovieDetails(
            movieId = movieId.toString(),
            onFailure = {
                Toast.makeText(this, "Details Section isn't succeeded", Toast.LENGTH_SHORT).show()
            })?.observe(this) {
            it?.let {
                bindData(it)
            }
        }

        mMovieModel.getCreditByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                actorsViewPod.setNewData(it.first)
                creatorsViewPod.setNewData(it.second)
            },
            onFailure = {
                Toast.makeText(this, "Details Section isn't succeeded", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun bindData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backDropPath}")
            .into(ivPosterMovieDetails)

        tvNameMovieDetails.text = movie.title ?: ""
        tvReleaseYearMovieDetails.text = movie.releaseDate?.substring(0, 3)
        tvRatingMovieDetails.text = movie.voteAverage?.toString() ?: ""
        movie.voteCount?.let {
            tvNumberOfVotesMovieDetails.text = "$it VOTES"
        }
        rbRatingMovieDetails.rating = movie.getRatingBaseOnFiveStars()
        bindGenre(movie, movie.genres ?: listOf())
        tvOverviewMovieDetails.text = movie.overview ?: ""
        tvOriginalTitle.text = movie.title ?: ""
        tvType.text = movie.getGenreAsCommaSeparatedString()
        tvProduction.text = movie.getProductionCountriesAsCommaSeparatedString()
        tvPremiere.text = movie.releaseDate ?: ""
        tvDescription.text = movie.overview ?: ""
    }

    private fun bindGenre(movie: MovieVO, genreList: List<GenreVO>) {
        movie.genres?.count()?.let {
            tvFirstGenre.text = genreList.firstOrNull()?.name ?: ""
            tvSecondGenre.text = genreList.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = genreList.getOrNull(2)?.name ?: ""

            if (it < 2) {
                tvSecondGenre.visibility = View.GONE
                tvThirdGenre.visibility = View.GONE
            } else if (it < 3) {
                tvThirdGenre.visibility = View.GONE
            }
        }
    }

    private fun setUpListeners() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setUpViewPods() {
        actorsViewPod = vpActors as ActorListViewPod
        actorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )

        creatorsViewPod = vpCreators as ActorListViewPod
        creatorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }
}