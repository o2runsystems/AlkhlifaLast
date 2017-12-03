package MangWorkFeature.ModelOprations;

import MangWorkFeature.ApiServices.MangWorkServices;
import MangWorkFeature.PojoClasses.Boxes;
import MangWorkFeature.PojoClasses.Famous;
import MangWorkFeature.PojoClasses.Reports;
import MangWorkFeature.Presenters.AddBoxesPresnter;
import MangWorkFeature.Presenters.AddContactPresnter;
import MangWorkFeature.Presenters.AddFamousePresnter;
import MangWorkFeature.Presenters.AddReportsPresenter;
import MangWorkFeature.Presenters.BoxesShowPresnter;
import MangWorkFeature.Presenters.ContactShowPresnter;
import MangWorkFeature.Presenters.FamouseShowPresnter;
import MangWorkFeature.Presenters.ReportsShowPresnter;
import PublishFeature.ApiServices.PublishServices;
import PublishFeature.PojoClasses.Publishs;
import PublishFeature.Presenters.AddPublishPresenter;
import PublishFeature.Presenters.NewsShowPresnter;
import Utiles.UrlHelpler;
import WebServices.Models.Contact;
import WebServices.Models.UrlInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MangWotrkOnlineIntractor {

    private Retrofit retrofit;
    private CompositeDisposable disposables = new CompositeDisposable();

    public MangWotrkOnlineIntractor(){
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UrlInfo.AppUrl)
                .build();
    }

    public void AddFamouse(Famous post , AddFamousePresnter presenter) {

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.AddFamous(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء النشر ");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void AddBoxes(Boxes post , AddBoxesPresnter presenter) {

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.AddBoxes(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء النشر ");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void AddReport(Reports post , AddReportsPresenter presenter) {

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.AddReport(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء النشر ");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void GetLiostOfFamouse(FamouseShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.GetAllFamous().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful()) {

                    presenter.OnlineDone(data.body());

                }else {

                    presenter.OnOlineError("خطـأ اثناء جلب البيانات");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void GetLiostOfReports(ReportsShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.GetAllReports().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful()) {

                    presenter.OnlineDone(data.body());

                }else {

                    presenter.OnOlineError("خطـأ اثناء جلب البيانات");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void GetLiostOfBoxes(BoxesShowPresnter presenter){
        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.GetAllBoxes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful()) {

                    presenter.OnlineDone(data.body());

                }else {

                    presenter.OnOlineError("خطـأ اثناء جلب البيانات");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void DeleteFamous(int id , FamouseShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.DeleteFamous(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {

                if (data.isSuccessful() && data.body()) {
                    presenter.OndeleteOnline();

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه الحذف");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void DeleteBoxes(int id , BoxesShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.DeleteBoxes(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {

                if (data.isSuccessful() && data.body()) {
                    presenter.OndeleteOnline();

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه الحذف");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void DeleteReports(int id , ReportsShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.DeleteReport(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {

                if (data.isSuccessful() && data.body()) {
                    presenter.OndeleteOnline();

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه الحذف");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void AddContact(Contact post , AddContactPresnter presenter) {

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.AddContact(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء الحفــظ ");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void GetContact(ContactShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            MangWorkServices publishServices = retrofit.create(MangWorkServices.class);
            Disposable creatuserobj = publishServices.GetContact().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful()) {

                    presenter.OnlineDone(data.body());

                }else {

                    presenter.OnOlineError("خطـأ اثناء جلب البيانات");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }


    public void OnDestory(){
        retrofit = null;
        disposables.dispose();
    }

}
