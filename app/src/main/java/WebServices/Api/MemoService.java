package WebServices.Api;

import java.util.List;

import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.Userses;
import WebServices.Models.logs_response;
import WebServices.Models.user_response;
import PublishFeature.PojoClasses.MarketModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dev2 on 10/1/2016.
 */
@SuppressWarnings("all")
public interface MemoService {

    @GET("Registertion/GetALl")
    Call<List<Userses>> GetAllUsers();

    @GET("Registertion/UpdatePer")
    Call<ResponseBody> UpdatePerForUser(@Query("id") int id , @Query("i") int i);

    @GET("Registertion/Sendmassage/")
    Call<String> Sendmessage(@Query("number")String number , @Query("msg")String msg);


    @POST("Registertion/Add/")
    Call<Boolean>  adding(@Body Userses u );

    @GET("Registertion/check_user/")
    Call<ResponseBody> check_user(@Query("user") String user );

    @GET("Registertion/Login/")
    Call<user_response> login(@Query("user") String user, @Query("pass") String pass );

    @GET("Registertion/GetLogs/")
    Call<logs_response> getlog(@Query("deviceid") String deviceid ,  @Query("userid") int userid);

    @GET("Registertion/Delete/")
    Call<ResponseBody> Delete(@Query("id") int userid);

    @GET("Registertion/GetCount/")
    Call<Integer> getlogCount(@Query("userid") int userid);

    @GET("Registertion/GetDeviceid/")
    Call<String[]> GetDeviceid(@Query("userid") int userid);

    @POST("Registertion/AddLogs/")
    Call<Boolean> AddLogs(@Body logs_response l);

    @GET("Registertion/Update")
    Call<ResponseBody> updateuser(@Query("id") int id, @Query("pass") String pass,@Query("phone") String phone , @Query("image") String img ,@Query("numofkid") Integer numofkid , @Query("jobc") String jobc ,@Query("ocpp") String ocpp );


}
