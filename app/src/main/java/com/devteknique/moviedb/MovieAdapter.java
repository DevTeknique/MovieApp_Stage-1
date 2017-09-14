package com.devteknique.moviedb;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import com.devteknique.moviedb.Utilities.MovieJsonUtils;
import com.devteknique.moviedb.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String [] mMoviePosterSrcPath;

    private final MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler{
        void onClick (String movieId);
    }

    MovieAdapter (MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }


    @Override
    public int getItemCount() {
        if(null == mMoviePosterSrcPath) return 0;
        return mMoviePosterSrcPath.length;

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_poster,parent,false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String posterURL = NetworkUtils.BASE_POSTER_URL + mMoviePosterSrcPath[position];
        Picasso.with(Movies.getAppContext()).load(posterURL).into(holder.mPosterImageView);

    }

    public void setPosterImage (String[] urls){
        mMoviePosterSrcPath = urls;
        notifyDataSetChanged();
    }


    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView mPosterImageView;

        MovieAdapterViewHolder(View view){
            super (view);
            mPosterImageView = view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        public void onClick (View v){
            int adapterPosition = getAdapterPosition();
            String movieId = MovieJsonUtils.movieID[adapterPosition];
            mClickHandler.onClick(movieId);
        }
    }
}
