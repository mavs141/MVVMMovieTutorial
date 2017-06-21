package chenyijie.mvvmmovietutorial.network;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class ApiManager {
    private static final String URL = "https://api.douban.com/";
    private static ApiManager instance;

    public static ApiManager getInstance(){
        if(instance == null) instance = new ApiManager();
        return instance;
    }

    public MoviesService getMovie(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MoviesService.class);
    }
}
