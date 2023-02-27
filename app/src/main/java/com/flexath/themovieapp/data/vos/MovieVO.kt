package com.flexath.themovieapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.flexath.themovieapp.persistence.typeconverters.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
@TypeConverters(
    GenreListTypeConverter::class,
    CollectionTypeConverter::class,
    ProductionCompanyTypeConverter::class,
    ProductionCountryTypeConverter::class,
    SpokenLanguageTypeConverter::class,
    GenreIdsTypeConverter::class
)
data class MovieVO(
    @SerializedName("adult")
    @ColumnInfo("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    @ColumnInfo("backdrop_path")
    val backDropPath: String?,

    @SerializedName("genre_ids")
    @ColumnInfo("genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("belongs_to_collection")
    @ColumnInfo("belongs_to_collection")
    val belongToCollection: CollectionVO?,

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    @SerializedName("original_language")
    @ColumnInfo("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    @ColumnInfo("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    @ColumnInfo("overview")
    val overview: String?,

    @SerializedName("popularity")
    @ColumnInfo("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    @ColumnInfo("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    @ColumnInfo("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    @ColumnInfo("title")
    val title: String?,

    @SerializedName("video")
    @ColumnInfo("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    @ColumnInfo("vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    @ColumnInfo("vote_count")
    val voteCount: Int?,

    @SerializedName("status")
    @ColumnInfo("status")
    val status: String?,

    @SerializedName("tagline")
    @ColumnInfo("tagline")
    val tagline: String?,

    @SerializedName("revenue")
    @ColumnInfo("revenue")
    val revenue: String?,

    @SerializedName("runtime")
    @ColumnInfo("runtime")
    val runtime: String?,

    @SerializedName("production_companies")
    @ColumnInfo("production_companies")
    val productionCompanies: List<ProductionCompanyVO>?,

    @SerializedName("production_countries")
    @ColumnInfo("production_countries")
    val productionCountries: List<ProductionCountryVO>?,

    @SerializedName("homepage")
    @ColumnInfo("homepage")
    val homepage: String?,

    @SerializedName("imdb_id")
    @ColumnInfo("imdb_id")
    val imdbId: String?,

    @SerializedName("budget")
    @ColumnInfo("budget")
    val budget: Long?,

    @SerializedName("genres")
    @ColumnInfo("genres")
    val genres: List<GenreVO>?,

    @SerializedName("spoken_languages")
    @ColumnInfo("spoken_languages")
    val spokenLanguages: List<SpokenLanguageVO>?,

    @ColumnInfo("type")
    var type:String?

) {
    fun getRatingBaseOnFiveStars(): Float {
        return voteAverage?.div(2)?.toFloat() ?: 0.0F
    }

    fun getGenreAsCommaSeparatedString(): String {
        return genres?.map {
            it.name
        }?.joinToString(",") ?: ""
    }

    fun getProductionCountriesAsCommaSeparatedString(): String {
        return productionCountries?.map {
            it.name
        }?.joinToString(",") ?: ""
    }
}

const val NOW_PLAYING = "NOW_PLAYING"
const val TOP_RATED = "TOP_RATED"
const val POPULAR = "POPULAR"