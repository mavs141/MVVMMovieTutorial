package chenyijie.mvvmmovietutorial.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import chenyijie.mvvmmovietutorial.BR;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class Movie extends BaseObservable {
    // 這裡的名稱需要和欄位名稱一致，不然會抓不到
    private String id;
    private String title;
    private String original_title;
    private String year;
    private Images images;
    private Rating rating;

    @Bindable
    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription(){
        return this.original_title + "\n" + this.year;
    }

    @Bindable
    public String getOriginal_title(){
        return this.original_title;
    }

    public void setOriginal_title(String original_title){
        this.original_title = original_title;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Images getImages(){
        return this.images;
    }

    public void setImages(Images images){
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public Rating getRating(){
        return this.rating;
    }

    public void setRating(Rating rating){
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear(){
        return this.year;
    }

    public void setYear(String year){
        this.year = year;
        notifyPropertyChanged(BR.description);
    }
}
