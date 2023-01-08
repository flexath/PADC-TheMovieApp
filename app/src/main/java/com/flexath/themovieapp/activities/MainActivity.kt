package com.flexath.themovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.themovieapp.R
import com.flexath.themovieapp.adapters.BannerAdapter
import com.flexath.themovieapp.adapters.ShowCaseAdapter
import com.flexath.themovieapp.delegates.BannerViewHolderDelegate
import com.flexath.themovieapp.delegates.MovieViewHolderDelegate
import com.flexath.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.flexath.themovieapp.dummy.dummyGenreList
import com.flexath.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),BannerViewHolderDelegate,ShowCaseViewHolderDelegate,MovieViewHolderDelegate {

    private lateinit var mBannerAdapter:BannerAdapter
    private lateinit var mShowCaseAdapter:ShowCaseAdapter

    private lateinit var mBestPopularMovieListViewPod:MovieListViewPod
    private lateinit var mMoviesByGenerViewPod:MovieListViewPod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        setUpViewPodsInstances()
        setUpBannerViewPager()
        setUpGenreTabLayout()
        setUpShowCaseRecyclerView()

        setUpListeners()
    }

    private fun setUpViewPodsInstances() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenerViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenerViewPod.setUpMovieListViewPod(this)
    }

    private fun setUpListeners() {
        // Genre Tab Layout
        tabLayoutGenre.addOnTabSelectedListener(object:OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Snackbar.make(window.decorView,tab?.text ?: "",Snackbar.LENGTH_LONG).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpGenreTabLayout() {
        dummyGenreList.forEach {
            tabLayoutGenre.newTab().apply {
                text = it
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(this)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
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
        menuInflater.inflate(R.menu.menu_discover,menu)
        return true
    }

    override fun onTapMovieFromBanner() {
        startActivity(MovieDetailsActivity.newIntent(this))
        Snackbar.make(window.decorView,"Tapped Movie From Banner",Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovieFromShowCase() {
        startActivity(MovieDetailsActivity.newIntent(this))
        Snackbar.make(window.decorView,"Tapped Movie From ShowCase",Snackbar.LENGTH_LONG).show()
    }

    override fun onTapMovie() {
        startActivity(MovieDetailsActivity.newIntent(this))
        Snackbar.make(window.decorView,"Tapped Movie From Best Popular Movies or Movies by Genre",Snackbar.LENGTH_LONG).show()
    }
}