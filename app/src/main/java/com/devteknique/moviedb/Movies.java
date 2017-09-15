package com.devteknique.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.devteknique.moviedb.Utilities.BackgroundTasks;


public class Movies extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    public static Object context;
    RecyclerView moviePosters;
    public static MovieAdapter mMovieAdapter;
    BackgroundTasks backgroundTasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        context = getApplicationContext();

        //Setting Up RecyclerView with GridLayout Manager
        moviePosters = (RecyclerView) findViewById(R.id.recyclerview_movies);
        moviePosters.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,numOfColumns());
        moviePosters.setLayoutManager(gridLayoutManager);
        mMovieAdapter = new MovieAdapter(this);
        moviePosters.setAdapter(mMovieAdapter);
        backgroundTasks = new BackgroundTasks();
        backgroundTasks.getMoviePosters("popular");

    }

    @Override
    public void onClick(String movieId){
        Intent intent = new Intent(this,MovieDetailActivity.class)
                .putExtra("MOVIE_ID",movieId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_by,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_by_popular) {
            backgroundTasks.getMoviePosters("popular");
            return true;
        }
        if  (id == R.id.action_sort_by_top_rated) {
            backgroundTasks.getMoviePosters("top_rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Function to get Application Context
    public static Context getAppContext () {
        return (Context)context;
    }

    //Function to get Num of Columns

    public int numOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }



}
