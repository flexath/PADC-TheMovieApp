package com.flexath.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.themovieapp.R
import com.flexath.themovieapp.adapters.BannerAdapter
import com.flexath.themovieapp.adapters.ShowCaseAdapter
import com.flexath.themovieapp.data.vos.GenreVO
import com.flexath.themovieapp.delegates.BannerViewHolderDelegate
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.mvvm.MainViewModel
import com.flexath.themovieapp.viewpods.ActorListViewPod
import com.flexath.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {

    private lateinit var mBannerAdapter: BannerAdapter
    private lateinit var mShowCaseAdapter: ShowCaseAdapter

    private lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    private lateinit var mMoviesByGenerViewPod: MovieListViewPod
    private lateinit var mActorViewPod: ActorListViewPod

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

        setUpToolBar()
        setUpViewPodsInstances()          // For best popular films and serial & genre movies
        setUpBannerViewPager()            // For Banner
        setUpShowCaseRecyclerView()       // For showcase
        setUpListeners()                 // For Genre Tab Layout

        observeLiveData()
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getInitialData()
    }

    private fun observeLiveData() {
        mViewModel.nowPlayingMoviesLiveData?.observe(this,mBannerAdapter::setData)
        mViewModel.popularMoviesLiveData?.observe(this,mBestPopularMovieListViewPod::setNewData)
        mViewModel.topRatedMoviesLiveData?.observe(this,mShowCaseAdapter::setNewData)
        mViewModel.genresLiveData.observe(this,this::setUpGenreTabLayout)
        mViewModel.moviesByGenreLiveData.observe(this,mMoviesByGenerViewPod::setNewData)
        mViewModel.actorsLiveData.observe(this,mActorViewPod::setNewData)
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
                mViewModel.getMoviesByGenre(tab?.position ?: 0)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btnSearch -> {
                startActivity(MovieSearchActivity.newIntent(this))
                return true
            }
            else -> false
        }
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