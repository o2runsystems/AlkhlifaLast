package PublishFeature.PojoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.TimeZone;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dev2 on 10/9/2016.
 */
public class Publishs extends RealmObject {

        @SerializedName("id")
        @Expose
        
        @PrimaryKey
        public int id;

        @SerializedName("userName")
        @Expose
        public String UserName;

        @SerializedName("title")
        @Expose
        public String Title;

        @SerializedName("body")
        @Expose
        public String Body;


        @SerializedName("avatar")
        @Expose
        public String Avatar;


        @SerializedName("images")
        @Expose
        public String Images;

        @SerializedName("type")
        @Expose
        public int Type;

        @SerializedName("dateTime")
        @Expose

        public Date DateTime;

        @SerializedName("isConfermet")
        @Expose

        public boolean isConfermet;



}



