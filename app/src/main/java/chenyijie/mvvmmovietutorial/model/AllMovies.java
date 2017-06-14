package chenyijie.mvvmmovietutorial.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * API回傳得資料，只有subjects是我們關心的
 *
 * Created by chenyijie on 2017/6/13.
 */

public class AllMovies {
    @SerializedName("subjects") private List<Movie> movies;

    public void setMovies(List<Movie> movies){
        this.movies = movies;
    }

    public List<Movie> getMovies(){
        return this.movies;
    }
}
