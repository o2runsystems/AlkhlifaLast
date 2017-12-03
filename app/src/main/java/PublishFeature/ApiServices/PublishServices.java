package PublishFeature.ApiServices;

import java.util.List;

import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.PojoClasses.Publishs;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

@SuppressWarnings("all")

public interface PublishServices {

    @POST("Publish/Add")
    Observable<Response<Integer>> addpublish(@Body Publishs publishs);

    @GET("Publish/GetALlNews")
    Observable<Response<List<Publishs>>> getpublishnews(@Query("id") int id);

    @GET("Publish/GetALlEvent")
    Observable<Response<List<Publishs>>> getpublishEvent(@Query("id") int id);

    @GET("Publish/GetALlhis")
    Observable<Response<List<Publishs>>> getpublishis();

    @GET("Publish/GetALlpor")
    Call<List<Publishs>> getpublishpor(@Query("id")  int id);


    @GET("Market/GetAll")
    Observable<Response<List<MarketModel>>> getmarket(@Query("id") int id);

    @POST("Market/Add")
    Observable<Response<Integer>> addmarket(@Body MarketModel model);


    @GET("Publish/ChangeConferm")
    Observable<Response<Boolean>> Confirmtions(@Query("idofpost") int id);

    @GET("Publish/Delete")
    Observable<Response<Boolean>> DeletePosts(@Query("id") int id);

    @GET("Market/Delete")
    Observable<Response<Boolean>> DeletePostsMarket(@Query("id") int id);

    @GET("Market/ConfermMarket")
    Observable<Response<Boolean>> ConfirmtionMarket(@Query("id")  int id);





}
