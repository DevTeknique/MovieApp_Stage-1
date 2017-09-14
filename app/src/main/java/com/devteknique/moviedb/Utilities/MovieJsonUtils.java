package com.devteknique.moviedb.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.concurrent.ConcurrentHashMap;





public final class MovieJsonUtils {
    public static String[] movieID;

    static String[] getMovieThumbnailPath (String moviePosterJson) throws JSONException {
        final String MDB_MESSAGE_CODE = "status_message";
        final String MDB_RESULTS = "results";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_MOVIE_ID = "id";
        String thumbnailPath;
        String[] parsedThumbnailPath;
        JSONObject jsonMovies = new JSONObject(moviePosterJson);

        if(jsonMovies.has(MDB_MESSAGE_CODE)){
            int errorCode = jsonMovies.getInt(MDB_MESSAGE_CODE);

            switch (errorCode){
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }
        JSONArray jsonResultsArray = jsonMovies.getJSONArray(MDB_RESULTS);
        parsedThumbnailPath = new String[jsonResultsArray.length()];
        movieID = new String [jsonResultsArray.length()];

        for (int i = 0; i < jsonResultsArray.length(); i++){
            JSONObject movie = jsonResultsArray.getJSONObject(i);
            thumbnailPath = movie.getString(MDB_POSTER_PATH);
            parsedThumbnailPath[i] = thumbnailPath;
            movieID[i] = movie.getString(MDB_MOVIE_ID);
        }
        return parsedThumbnailPath;
    }

    public static ConcurrentHashMap<String, String> getMovieDetails (String jsonSelectedMovie) throws JSONException {
        ConcurrentHashMap<String,String> movieDetails = new ConcurrentHashMap<>();
        final String MDB_MESSAGE_CODE = "status_message";
        JSONObject jsonMovieDetails = new JSONObject(jsonSelectedMovie);

        if(jsonMovieDetails.has(MDB_MESSAGE_CODE)){
            int errorCode = jsonMovieDetails.getInt(MDB_MESSAGE_CODE);

            switch (errorCode){
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        movieDetails.put("original_title",jsonMovieDetails.getString("original_title"));
        movieDetails.put("release_date",jsonMovieDetails.getString("release_date"));
        movieDetails.put("runtime",jsonMovieDetails.getString("runtime"));
        movieDetails.put("vote_average",jsonMovieDetails.getString("vote_average"));
        movieDetails.put("overview",jsonMovieDetails.getString("overview"));
        movieDetails.put("poster_path",jsonMovieDetails.getString("poster_path"));
        return movieDetails;
    }
}
