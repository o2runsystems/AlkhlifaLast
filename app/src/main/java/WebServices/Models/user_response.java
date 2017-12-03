package WebServices.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dev2 on 10/2/2016.
 */
public class user_response extends RealmObject {

    @PrimaryKey
    public int id;
    public String civilNumber ;

    public String userName ;

    public String Pass ;

    public String fname ;

    public String ffName ;

    public String fffName ;

    public String ffffName ;

    public Boolean gender ;

    public String mainBar ;

    public String levelOfEdcation ;

    public String numberOfKids ;

    public String mobile ;

    public String job ;

    public String compony ;

    public String fatherId ;

    public String dateoFbearth ;

    public String email ;

    public String city ;

    public String picId ;

    public String idCopy ;

    public String state ;

    public Integer perid;


}
