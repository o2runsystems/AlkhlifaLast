package WebServices.Models;

import io.realm.RealmObject;

/**
 * Created by dev on 12/27/2016.
 */
public class Profiles {

    public Profiles(String name , String sta){
        this.Name = name;
        this.Status = sta;
    }

    public int Id ;
    public String Name ;
    public String Pic ;
    public String Status ;

}
