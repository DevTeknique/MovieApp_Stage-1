package com.devteknique.moviedb.Utilities;

import android.net.Uri;
import com.devteknique.moviedb.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class NetworkUtils {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String BASE_DETAIL_URL = "https://api.themoviedb.org/3/movie/";
    private final static String API_PARAM = "api_key";
    private final static String LANGUAGE_PARAM = "language";
    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private static String languageSetting = "en-US";
    private static final String API_KEY = BuildConfig.API_KEY;

    static URL buildThumbnailUrl(String sortType) {
        URL url = null;
        Uri builtUri = Uri.parse(BASE_URL+sortType)
                .buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, languageSetting)
                .build();
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildDetailURL (String movieId){
        URL url = null;
        Uri builtUri = Uri.parse(BASE_DETAIL_URL+movieId)
                .buildUpon()
                .appendQueryParameter(API_PARAM,API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, languageSetting)
                .build();
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException{
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try{
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }else{
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
    }
}