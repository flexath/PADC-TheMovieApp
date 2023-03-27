package com.flexath.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.themovieapp.R
import com.flexath.themovieapp.adapters.BannerAdapter
import com.flexath.themovieapp.adapters.ShowCaseAdapter
import com.flexath.themovieapp.data.vos.ActorVO
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.mvp.presenters.MainPresenter
import com.flexath.themovieapp.mvp.presenters.MainPresenterImpl
import com.flexath.themovieapp.mvp.views.MainView
import com.flexath.themovieapp.viewpods.ActorListViewPod
import com.flexath.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView{

    private lateinit var mBannerAdapter: BannerAdapter
    private lateinit var mShowCaseAdapter: ShowCaseAdapter

    private lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    private lateinit var mMoviesByGenreViewPod: MovieListViewPod
    private lateinit var mActorViewPod: ActorListViewPod

    private lateinit var mPresenter:MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()

        setUpToolBar()
        setUpViewPodsInstances()          // For best popular films and serial & genre movies
        setUpBannerViewPager()            // For Banner
        setUpShowCaseRecyclerView()       // For showcase
        setUpListeners()                 // For Genre Tab Layout

        mPresenter.onUIReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpViewPodsInstances() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mActorViewPod = vpActorHome as ActorListViewPod
    }

    private fun setUpListeners() {

        // Genre Tab Layout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
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
        mShowCaseAdapter = ShowCaseAdapter(mPresenter)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(mPresenter)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btnSearch -> {
                startActivity(MovieSearchActivity.newIntent(this))
                return true
            }
            else -> false
        }
    }

    override fun showNowPlayingMovies(movieList: List<MovieVO>) {
        mBannerAdapter.setData(movieList)
    }

    override fun showPopularMovies(movieList: List<MovieVO>) {
        mBestPopularMovieListViewPod.setNewData(movieList)
    }

    override fun showTopRatedMovies(movieList: List<MovieVO>) {
        mShowCaseAdapter.setNewData(movieList)
    }

    override fun showMoviesByGenre(movieList: List<MovieVO>) {
        mMoviesByGenreViewPod.setNewData(movieList)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showActors(actorList: List<ActorVO>) {
        mActorViewPod.setNewData(actorList)
    }

    override fun navigateToMovieDetailScreen(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}