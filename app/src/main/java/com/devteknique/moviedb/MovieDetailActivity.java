package com.devteknique.moviedb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.devteknique.moviedb.Utilities.MovieJsonUtils;
import com.devteknique.moviedb.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    String movieID;
    @BindView(R.id.tv_movie_title) TextView movieTitle;
    @BindView(R.id.tv_movie_year_released) TextView movieYearReleased;
    @BindView(R.id.tv_movie_length) TextView movieRuntime;
    @BindView(R.id.tv_movie_user_rating) TextView movieUserRating;
    @BindView(R.id.tv_movie_overview) TextView movieOverView;
    @BindView(R.id.iv_movie_poster) ImageView moviePoster;
    @BindView(R.id.cl_movie_detail) ConstraintLayout detailLayout;
    @BindView(R.id.pb_loading_circle) ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_details);
        //Bind Views
        ButterKnife.bind(this);



        //Get information from Intent
        Intent intent = getIntent();
        movieID=intent.getStringExtra("MOVIE_ID");

        //Execute AsyncTask to get Movie Details
        new FetchMovieDetails().execute(movieID);
    }


    public void showDetails (){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        detailLayout.setVisibility(View.VISIBLE);
    }


    public void populateMovieDetails (ConcurrentHashMap hashMap){
        movieTitle.setText(hashMap.get("original_title").toString());
        movieYearReleased.setText(hashMap.get("release_date").toString());
        movieRuntime.setText(getString(R.string.movie_runtime,hashMap.get("runtime").toString()));
        movieUserRating.setText(getString(R.string.movie_user_rating,hashMap.get("vote_average").toString()));
        movieOverView.setText(hashMap.get("overview").toString());
        Picasso.with(this).load(NetworkUtils.BASE_POSTER_URL+hashMap.get("poster_path")).into(moviePoster);
        showDetails();
    }

    private class FetchMovieDetails extends AsyncTask<String,Void,ConcurrentHashMap> {
        ConcurrentHashMap hashMap = new ConcurrentHashMap();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ConcurrentHashMap doInBackground(String... movieID) {
            if (movieID[0].equals(""))
                return null;
            URL movieRequestUrl = NetworkUtils.buildDetailURL(movieID[0]);

            try{
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                hashMap = MovieJsonUtils.getMovieDetails(jsonMovieResponse);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return hashMap;
        }
        @Override
        protected void onPostExecute(ConcurrentHashMap hashMap) {
            populateMovieDetails(hashMap);
        }
    }
}
