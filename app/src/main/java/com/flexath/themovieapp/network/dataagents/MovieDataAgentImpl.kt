package com.flexath.themovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.flexath.themovieapp.data.vos.MovieVO
import com.flexath.themovieapp.network.responses.MovieListResponse
import com.flexath.themovieapp.utils.API_GET_NOW_PLAYING
import com.flexath.themovieapp.utils.BASE_URL
import com.flexath.themovieapp.utils.MOVIE_API_KEY
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//object MovieDataAgentImpl : MovieDataAgent {
//
//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        GetNowPlayingMovieTask().execute()
//    }
//
//    class GetNowPlayingMovieTask : AsyncTask<Void,Void, MovieListResponse>(){
//        override fun doInBackground(vararg p0: Void?): MovieListResponse? {
//            val url:URL
//            var reader: BufferedReader? = null
//            val stringBuilder: StringBuilder
//
//            try {
//                url =
//                    URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
//                val connection = url.openConnection() as HttpURLConnection
//
//                // set HTTP Method
//                connection.requestMethod = "GET"
//                connection.readTimeout = 5 * 1000
//                connection.doInput = true
//                connection.doOutput = false
//                connection.connect()
//
//                // read Strings line by line from Network
//                reader = BufferedReader(InputStreamReader(connection.inputStream))
//                stringBuilder = StringBuilder()
//
//                for (line in reader.readLine()) {
//                    stringBuilder.append(line + "\n")
//                }
//
//                val responseString = stringBuilder.toString()
//                Log.d("NowPlayingMovies", responseString)
//
//                return Gson().fromJson(responseString, MovieListResponse::class.java)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.e("NewsError", e.message ?: "")
//            } finally {
//                if (reader != null) {
//                    try {
//                        reader.close()
//                    } catch (ioe: IOException) {
//                        ioe.printStackTrace()
//                    }
//                }
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: MovieListResponse?) {
//            super.onPostExecute(result)
//        }
//    }
//}