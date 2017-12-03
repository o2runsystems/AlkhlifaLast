package WebServices.Api;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by dev on 10/8/2016.
 */
public interface BookLibServices {
    @POST("BookLib/AddNew")
    Call<ResponseBody> InsertNewBookLib(@Body WebServices.Models.BookLibModel model);
    @POST("BookLib/Update")
    Call<ResponseBody> UpdateBookLib(@Query("id") int id , @Body WebServices.Models.BookLibModel model);
    @GET("BookLib/Delete")
    Call<ResponseBody> DeleteBookLib(@Query("id") int id);
    @GET("BookLib/GetAll")
    Call<List<WebServices.Models.BookLibModel>> GetAllBook(@Query("id") int id);

}
