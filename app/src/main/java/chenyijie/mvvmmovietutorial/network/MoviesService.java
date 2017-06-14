package chenyijie.mvvmmovietutorial.network;

import chenyijie.mvvmmovietutorial.model.AllMovies;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 豆瓣電影API:
 *      https://api.douban.com/v2/movie/search?tag=科幻start=0&count=20
 *
 * Created by chenyijie on 2017/6/13.
 */

public interface MoviesService {
    @GET("v2/movie/search")
    Observable<AllMovies> getAllMovies(@Query("tag") String tag
            , @Query("start") int start, @Query("count") int count);
}
