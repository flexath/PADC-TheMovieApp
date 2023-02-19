package com.flexath.themovieapp.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.themovieapp.R
import com.flexath.themovieapp.adapters.BannerAdapter
import com.flexath.themovieapp.adapters.ShowCaseAdapter
import com.flexath.themovieapp.data.models.MovieModel
import com.flexath.themovieapp.data.models.MovieModelImpl
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.delegates.BannerViewHolderDelegate
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.viewpods.ActorListViewPod
import com.flexath.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {

    private lateinit var mBannerAdapter: BannerAdapter
    private lateinit var mShowCaseAdapter: ShowCaseAdapter

    private lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    private lateinit var mMoviesByGenerViewPod: MovieListViewPod
    private lateinit var mActorViewPod: ActorListViewPod

    private var mMovieModel: MovieModel = MovieModelImpl
    private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        setUpViewPodsInstances()          // For best popular films and serial & genre movies
        setUpBannerViewPager()            // For Banner
        setUpShowCaseRecyclerView()       // For showcase
        setUpListeners()                 // For Genre Tab Layout

        requestData()
    }

    private fun requestData() {
        mMovieModel.getNowPlayingMovies(
            onSuccess = {
                mBannerAdapter.setData(it)
            },
            onFailure = {
                Toast.makeText(this, "Banner Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieModel.getPopularMovies(
            onSuccess = {
                mBestPopularMovieListViewPod.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "Popular Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieModel.getTopRatedMovies(
            onSuccess = {
                mShowCaseAdapter.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "TopRated Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                setUpGenreTabLayout(it)

                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenre(genreId)
                }
            },
            onFailure = {
                Toast.makeText(this, "Genre Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieModel.getActors(
            onSuccess = {
                mActorViewPod.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "Actor Section isn't succeed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun getMoviesByGenre(genreId: Int) {
        mMovieModel.getMoviesByGenre(
            genreId = genreId.toString(),
            onSuccess = {
                mMoviesByGenerViewPod.setNewData(it)
            },
            onFailure = {
                Toast.makeText(this, "MovieByGenre Section isn't succeed", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }


    private fun setUpViewPodsInstances() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenerViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenerViewPod.setUpMovieListViewPod(this)

        mActorViewPod = vpActorHome as ActorListViewPod
    }

    private fun setUpListeners() {
        // Genre Tab Layout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("TabActivity", tab?.position.toString())
                mGenres?.get(tab?.position ?: 0)?.id?.let {
                    getMoviesByGenre(it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpGenreTabLayout(genreList: List<GenreVO>) {
        genreList.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(this)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter
        dotsIndicatorBanner.attachTo(viewPagerBanner)
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
        Snackbar.make(window.decorView, "Tapped Movie From Banner", Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
        Snackbar.make(window.decorView, "Tapped Movie From ShowCase", Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
        Snackbar.make(
            window.decorView,
            "Tapped Movie From Best Popular Movies or Movies by Genre",
            Snackbar.LENGTH_LONG
        ).show()
    }
}