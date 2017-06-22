package chenyijie.mvvmmovietutorial.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.design.widget.Snackbar;
import android.view.View;

import chenyijie.mvvmmovietutorial.model.Movie;

/**
 * Created by chenyijie on 2017/6/22.
 */

public class ItemMovieViewModel extends BaseObservable {
//    private Context context;
    private Movie movie;

//    public ItemMovieViewModel(Context context, Movie movie){
//        this.context = context;
//        this.movie = movie;
//    }

    public ItemMovieViewModel(Movie movie){
        this.movie = movie;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
        notifyChange();
    }

    public String getTitle(){
        return movie.getTitle();
    }

    public String getDescription(){
        return movie.getDescription();
    }

    public float getAverage(){
        return movie.getRating().getAverage();
    }

    public void onItemClick(View view){
        Snackbar.make(view, "選擇電影: " + movie.getTitle(), Snackbar.LENGTH_SHORT).show();
    }
}
