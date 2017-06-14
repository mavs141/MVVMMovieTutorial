package chenyijie.mvvmmovietutorial.model;

import java.io.Serializable;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class Images implements Serializable {
    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
