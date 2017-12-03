package WebServices.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BookLibModel  extends RealmObject{
    @PrimaryKey
    public int id;
    public String name ;
    public String author;
    public String downloadurl;
    public String imgId;
    public String bookDescrib;
}
