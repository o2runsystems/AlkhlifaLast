package WebServices.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dev on 10/8/2016.
 */
public class ImgLibModel extends RealmObject {
    @PrimaryKey
    public int id;
    public String name;
    public String sumrize;
    public String imgurl;
}
