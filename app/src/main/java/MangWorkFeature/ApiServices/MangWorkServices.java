package MangWorkFeature.ApiServices;

import java.util.List;

import MangWorkFeature.PojoClasses.Boxes;
import MangWorkFeature.PojoClasses.Famous;
import MangWorkFeature.PojoClasses.Reports;
import WebServices.Models.Contact;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MangWorkServices {

    @POST("Famous/Add")
    Observable<Response<Integer>> AddFamous(@Body Famous post);

    @GET("Famous/GetAll")
    Observable<Response<List<Famous>>> GetAllFamous();

    @GET("Famous/Delete")
    Observable<Response<Boolean>> DeleteFamous(@Query("id") int id);


    @POST("Box/Add")
    Observable<Response<Integer>> AddBoxes(@Body Boxes post);

    @GET("Box/GetAll")
    Observable<Response<List<Boxes>>> GetAllBoxes();

    @GET("Box/Delete")
    Observable<Response<Boolean>> DeleteBoxes(@Query("id") int id);



    @POST("Report/Add")
    Observable<Response<Integer>> AddReport(@Body Reports post);

    @GET("Report/GetAll")
    Observable<Response<List<Reports>>> GetAllReports();

    @GET("Report/Delete")
    Observable<Response<Boolean>> DeleteReport(@Query("id") int id);


    @POST("ContactUs/Add")
    Observable<Response<Integer>> AddContact(@Body Contact post);

    @GET("ContactUs/GetAll")
    Observable<Response<Contact>> GetContact();

    @GET("ContactUs/Delete")
    Observable<Response<Boolean>> DeletContac(@Query("id") int id);
}
