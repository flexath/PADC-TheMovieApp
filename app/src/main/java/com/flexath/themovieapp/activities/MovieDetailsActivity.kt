package com.flexath.themovieapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.flexath.themovieapp.R
import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.mvp.presenters.MovieDetailPresenter
import com.flexath.themovieapp.mvp.presenters.MovieDetailPresenterImpl
import com.flexath.themovieapp.mvp.views.MovieDetailsView
import com.flexath.themovieapp.utils.IMAGE_BASE_URL
import com.flexath.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(),MovieDetailsView {

    // ViewPods
    private lateinit var actorsViewPod: ActorListViewPod
    private lateinit var creatorsViewPod: ActorListViewPod

    private lateinit var mPresenter:MovieDetailPresenter

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

        setUpPresenter()

        setUpViewPods()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let {
            mPresenter.onUIReadyInMovieDetails(this,it)
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MovieDetailPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    @SuppressLint("SetTextI18n")
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
            mPresenter.onTapBack()
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

    override fun showMovieDetails(movie: MovieVO) {
        bindData(movie)
    }

    override fun showCreditsByMovie(castList: List<ActorVO>, crewList: List<ActorVO>) {
        actorsViewPod.setNewData(castList)
        creatorsViewPod.setNewData(crewList)
    }

    override fun navigateBack() {
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}