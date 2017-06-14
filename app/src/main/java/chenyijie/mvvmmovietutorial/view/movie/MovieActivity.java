package chenyijie.mvvmmovietutorial.view.movie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import chenyijie.mvvmmovietutorial.R;
import chenyijie.mvvmmovietutorial.model.AllMovies;
import chenyijie.mvvmmovietutorial.network.ApiManager;
import chenyijie.mvvmmovietutorial.network.WebService;
import chenyijie.mvvmmovietutorial.databinding.ActivityMovieBinding;
import chenyijie.mvvmmovietutorial.model.Movie;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class MovieActivity extends AppCompatActivity {
    private ActivityMovieBinding binding;
    private CompositeSubscription compositeSubscription ;

    @Override
    protected void onCreate(Bundle savedInstanceStates){
        super.onCreate(savedInstanceStates);
        initBinding();
        doSearch(getString(R.string.default_search_tag));
    }

    private void initBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        initComponent();
    }

    private void initComponent(){
        compositeSubscription = new CompositeSubscription();

        binding.toolbar.setTitle("電影列表");
        setSupportActionBar(binding.toolbar);

        binding.movieList.setHasFixedSize(true);
        binding.movieList.setLayoutManager(new LinearLayoutManager(this));
        binding.movieList.setItemAnimator(new DefaultItemAnimator());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MovieActivity.this)
                        .title(R.string.search)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString());
                                }
                            }
                        }).show();
            }
        });
    }

    private void doSearch(String keyword){
        binding.progressBar.setVisibility(View.VISIBLE);
        Subscription subscription = ApiManager.getInstance().getMovie()
                .getAllMovies(keyword, 0, 20)
                .flatMap(new Func1<AllMovies, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(AllMovies allMovies) {
                        return Observable.from(allMovies.getMovies());
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        MovieAdapter adapter = new MovieAdapter(MovieActivity.this, movies);
                        binding.movieList.setAdapter(adapter);
                    }
                });
        compositeSubscription.add(subscription);
        /*
        WebService.searchMovies(keyword, new WebService.IMovieResponse<List<Movie>>() {
            @Override
            public void onData(List<Movie> data) {
                MovieAdapter adapter = new MovieAdapter(MovieActivity.this, data);
                binding.movieList.setAdapter(adapter);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
        */
    }
}
