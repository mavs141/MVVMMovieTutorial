package chenyijie.mvvmmovietutorial.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import chenyijie.mvvmmovietutorial.R;
import chenyijie.mvvmmovietutorial.model.AllMovies;
import chenyijie.mvvmmovietutorial.model.Movie;
import chenyijie.mvvmmovietutorial.network.ApiManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenyijie on 2017/6/21.
 */

public class MovieViewModel extends java.util.Observable {
    // 記得要設定為public，data binding才找得到
    public ObservableInt movieProgress;
    public ObservableInt movieRecycler;

    private Context context;
    private List<Movie> movieList;
//    private CompositeSubscription compositeSubscription ;
    private CompositeDisposable compositeDisposable;

    public MovieViewModel(@NonNull Context context){
        this.movieProgress = new ObservableInt(View.GONE);
        this.movieRecycler = new ObservableInt(View.GONE);

        this.context = context;
        this.movieList = new ArrayList<>();
//        compositeSubscription = new CompositeSubscription();
        compositeDisposable = new CompositeDisposable();

        doSearch(context.getString(R.string.default_search_tag));
    }

    public void onFabClick(View view){
        initView();
        showDialog();
    }

    private void initView(){
//        this.movieProgress = new ObservableInt(View.VISIBLE);
        this.movieRecycler = new ObservableInt(View.GONE);
    }

    private MaterialDialog showDialog(){
        return new MaterialDialog.Builder(context)
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

    private void doSearch(String keyword){
        movieProgress.set(View.VISIBLE);
        // rxjava 1
//        Subscription subscription = ApiManager.getInstance().getMovie()
//                .getAllMovies(keyword, 0, 20)
//                .flatMap(new Func1<AllMovies, Observable<Movie>>() {
//                    @Override
//                    public Observable<Movie> call(AllMovies allMovies) {
//                        return Observable.from(allMovies.getMovies());
//                    }
//                })
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Movie>>() {
//                    @Override
//                    public void onCompleted() {
//                        movieRecycler.set(View.VISIBLE);
//                        movieProgress.set(View.GONE);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        movieRecycler.set(View.GONE);
//                        movieProgress.set(View.GONE);
//                    }
//
//                    @Override
//                    public void onNext(List<Movie> movies) {
//                        changeMovieList(movies);
//                    }
//                });
//        compositeSubscription.add(subscription);
        Disposable disposable = ApiManager.getInstance().getMovie()
                .getAllMovies(keyword, 0, 20)
                .flatMap(new Function<AllMovies, ObservableSource<Movie>>() {
                    @Override
                    public ObservableSource<Movie> apply(AllMovies allMovies) throws Exception {
                        return Observable.fromIterable(allMovies.getMovies());
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        changeMovieList(movies);
                        movieRecycler.set(View.VISIBLE);
                        movieProgress.set(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        movieRecycler.set(View.GONE);
                        movieProgress.set(View.GONE);
                    }
                });
        compositeDisposable.add(disposable);
        /*
        WebService.searchMovies(keyword, new WebService.IMovieResponse<List<Movie>>() {
            @Override
            public void onData(List<Movie> data) {
                MovieAdapter adapter = new MovieAdapter(MovieActivity.this, data);
                binding.movieList.setAdapter(adapter);
                binding.movieProgress.setVisibility(View.GONE);
            }
        });
        */
    }

    /**
     * 資料變動以後會透過觀察者模式告知Activity
     * */
    private void changeMovieList(List<Movie> movieList){
        this.movieList.clear();
        this.movieList.addAll(movieList);
        setChanged();
        notifyObservers();
    }

    public List<Movie> getMovieList(){
        return this.movieList;
    }

    public void onStop(){
        if(compositeDisposable != null && !compositeDisposable.isDisposed() )
            compositeDisposable.dispose();
        compositeDisposable = null;
        // Rxjava1
//        if(compositeSubscription != null && !compositeSubscription.isUnsubscribed())
//            compositeSubscription.unsubscribe();
//        compositeSubscription = null;
        context = null;
    }
}
