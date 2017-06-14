package chenyijie.mvvmmovietutorial.model;

/**
 * Created by chenyijie on 2017/6/13.
 */

public class User {
    private final String firstName;
    private final String lastName;

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

}
