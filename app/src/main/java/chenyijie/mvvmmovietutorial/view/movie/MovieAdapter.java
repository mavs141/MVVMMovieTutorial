package chenyijie.mvvmmovietutorial.view.movie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import chenyijie.mvvmmovietutorial.BR;
import chenyijie.mvvmmovietutorial.R;
import chenyijie.mvvmmovietutorial.databinding.MovieItemBinding;
import chenyijie.mvvmmovietutorial.model.Movie;
import chenyijie.mvvmmovietutorial.viewmodel.ItemMovieViewModel;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class MovieAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Movie> movies ;

    public MovieAdapter(Context context){
        this.context = context;
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 在adapter / fragment中，可以用inflat來取得binding
        MovieItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item,
                parent,
                false);
//        MovieHolder holder = new MovieHolder(binding.getRoot());
//        holder.setBinding(binding);
//        return holder;
        return new MovieHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie selectedMovie = movies.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;

        Glide.with(context).load(selectedMovie.getImages().getMedium())
                .fitCenter()
                .into(movieHolder.binding.imageMovie);
        //todo 研究一下這邊是幹嘛的
//        movieHolder.binding.setVariable(BR.movie, selectedMovie);
//        movieHolder.binding.executePendingBindings(); // 要求view holder立即綁定view
        movieHolder.bindMovie(selectedMovie);
    }

    @Override
    public int getItemCount() {
        if(movies == null) return 0;
        return movies.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{
        private MovieItemBinding binding ;

        public MovieHolder(View itemView) {
            super(itemView);
        }

        public MovieHolder(MovieItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public MovieItemBinding getBinding(){
            return this.binding;
        }

        public void setBinding(MovieItemBinding binding){
            this.binding = binding;
        }

        public void bindMovie(Movie movie){
            if(binding.getItemMovieViewModel() == null){
                binding.setItemMovieViewModel(new ItemMovieViewModel(movie));
            }else{
                binding.getItemMovieViewModel().setMovie(movie);
            }
        }

    }
}
