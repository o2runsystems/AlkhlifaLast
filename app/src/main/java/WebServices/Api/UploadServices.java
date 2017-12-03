package WebServices.Api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by dev on 10/9/2016.
 */

public interface UploadServices {
    @Multipart
    @POST("Uploads/UploadFile")
    Call<ResponseBody> UploadFile(@Part MultipartBody.Part part);
}
