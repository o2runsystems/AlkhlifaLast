package WebServices.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class logs_response extends RealmObject {
        @PrimaryKey

        public int Id ;
        public String Deviceid ;
        public String Networkname ;
        public String Stcssn ;
        public int Usersid;




}
