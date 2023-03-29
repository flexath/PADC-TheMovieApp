package com.flexath.themovieapp.data.models

import androidx.lifecycle.LiveData
import com.flexath.themovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : MovieModel, MovieBaseModel() {

//    private val mMovieDataAgent:MovieDataAgent = RetrofitDataAgentImpl

    override fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        mMovieApi.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mMovieDatabase?.movieDao()
            ?.getMoviesByType(NOW_PLAYING)
    }

    override fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {
        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(POPULAR)
    }

    override fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ) :LiveData<List<MovieVO>>? {
        mMovieApi.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(TOP_RATED)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {

        mMovieApi.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres)
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        mMovieApi.getMoviesByGenre(genreId = genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {

        mMovieApi.getActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetails(
        movieId: String,
        onFailure: (String) -> Unit
    ) : LiveData<MovieVO?>? {

        mMovieApi.getMovieDetails(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId.toInt())
                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMovieById(movieId.toInt())
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getCreditByMovie(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> {
        return mMovieApi.searchMovie(query = query)
            .map {
                it.results ?: listOf()
            }
            .onErrorResumeNext{
                Observable.just(listOf())
            }
            .subscribeOn(Schedulers.io())
    }

    // Reactive Streams

    override fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(NOW_PLAYING)
            ?.toObservable()
    }

    override fun getPopularMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }

        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(POPULAR)
            ?.toObservable()
    }

    override fun getTopRatedMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }

        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(TOP_RATED)
            ?.toObservable()
    }

    override fun getGenresObservable(): Observable<List<GenreVO>>? {
        return mMovieApi.getGenres()
            .map {
                it.genres
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getMoviesByGenreObservable(genreId: String): Observable<List<MovieVO>>? {
        return mMovieApi.getMoviesByGenre(genreId = genreId)
            .map {
                it.results ?: listOf()
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getActorsObservable(): Observable<List<ActorVO>>? {
        return mMovieApi.getActors()
            .map {
                it.results ?: listOf()
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getMovieDetailsObservable(movieId: String): Observable<MovieVO?>? {
        mMovieApi.getMovieDetails(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val movieFromDatabase =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId.toInt())
                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }

        return mMovieDatabase?.movieDao()?.getMovieByIdFlowable(movieId.toInt())
            ?.toObservable()
    }

    override fun getCreditByMovieObservable(movieId: String): Observable<Pair<List<ActorVO>, List<ActorVO>>> {
        return mMovieApi.getCreditByMovie(movieId = movieId)
            .map {
                Pair(it.cast ?: listOf(),it.crew ?: listOf())
            }
            .subscribeOn(Schedulers.io())
    }
}