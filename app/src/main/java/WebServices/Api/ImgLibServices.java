package WebServices.Api;

import WebServices.Models.ImgLibModel;
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
public interface ImgLibServices {
    @POST("ImgLibCo/AddNew")
    Call<ResponseBody> InsertNewImgLib(@Body ImgLibModel imglib);
    @POST("ImgLibCo/Update")
    Call<ResponseBody> UpdateNewImgLib(@Query("id") int id , ImgLibModel model);
    @GET("ImgLibCo/DeleteOn")
    Call<ResponseBody> DeleteImgLib(@Query("id") int id);
    @GET("ImgLibCo/GetAll")
    Call<List<ImgLibModel>> GetAllImgLib(@Query("id") int id);
}
