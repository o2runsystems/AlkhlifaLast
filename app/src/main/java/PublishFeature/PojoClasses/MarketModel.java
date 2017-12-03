package PublishFeature.PojoClasses;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dev2 on 10/12/2016.
 */

public class MarketModel extends RealmObject  {
    @PrimaryKey
    public int id ;
    public String img ;
    public String name ;
    public String about ;
    public String price;
    public String cat ;
    public String type ;
    public Date time ;
    public String phone;
    public boolean isConfermet;


}
