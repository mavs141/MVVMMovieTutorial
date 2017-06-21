package chenyijie.mvvmmovietutorial.view.movie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import chenyijie.mvvmmovietutorial.R;
import chenyijie.mvvmmovietutorial.databinding.ActivityMovieBinding;
import chenyijie.mvvmmovietutorial.viewmodel.MovieViewModel;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class MovieActivity extends AppCompatActivity implements java.util.Observer {
    private ActivityMovieBinding binding;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceStates){
        super.onCreate(savedInstanceStates);
        initBinding();
        initToolbar();
        setRecyclerView(binding.movieList);
        setObserver(viewModel);
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        viewModel = new MovieViewModel(this);
        binding.setMainViewModel(viewModel);
    }

    private void initToolbar(){
        binding.toolbar.setTitle(getResources().getString(R.string.toolbar_title));
        setSupportActionBar(binding.toolbar);
    }

    private void setRecyclerView(RecyclerView moveList){
        MovieAdapter adapter = new MovieAdapter(this);
        moveList.setAdapter(adapter);
        moveList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setObserver(java.util.Observable observable){
        observable.addObserver(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        viewModel.onStop();
    }

    @Override
    public void update(java.util.Observable observable, Object o) {
        if(observable instanceof MovieViewModel){
            MovieAdapter adapter = (MovieAdapter) binding.movieList.getAdapter();
            MovieViewModel viewModel = (MovieViewModel) observable;
            adapter.setMovies(viewModel.getMovieList());
        }
    }
}
