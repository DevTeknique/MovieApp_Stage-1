package com.devteknique.moviedb.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.devteknique.moviedb.Movies;
import com.devteknique.moviedb.R;

import java.net.URL;



public class BackgroundTasks {

    public BackgroundTasks (String sortType){
        new FetchThumbnails().execute(sortType);
    }
    //Class to retrieve movie poster thumbnails for RecyclerView
    private class FetchThumbnails extends AsyncTask<String,Void,String[]> {
        String [] thumbnailPaths;
        ConnectivityManager connectivityManager = (ConnectivityManager)Movies.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null;
        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0 || !isConnected )
                return null;
            String sortType = strings[0];
            URL movieRequestUrl = NetworkUtils.buildThumbnailUrl(sortType);

            try{
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                thumbnailPaths = MovieJsonUtils.getMovieThumbnailPath(jsonMovieResponse);


            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return thumbnailPaths;
        }
        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null){
                Movies.mMovieAdapter.setPosterImage(strings);
            }else{
                Toast.makeText(Movies.getAppContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
