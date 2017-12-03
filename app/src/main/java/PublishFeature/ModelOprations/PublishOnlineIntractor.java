package PublishFeature.ModelOprations;

import java.util.List;

import PublishFeature.ApiServices.PublishServices;
import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.PojoClasses.Publishs;
import PublishFeature.Presenters.AddPublishPresenter;
import PublishFeature.Presenters.EventShowPresnter;
import PublishFeature.Presenters.HistoryShowPresnter;
import PublishFeature.Presenters.MarketAddPostPresnter;
import PublishFeature.Presenters.MarketShowPresnter;
import PublishFeature.Presenters.NewsShowPresnter;
import Utiles.UrlHelpler;
import WebServices.Models.UrlInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class PublishOnlineIntractor {
    private Retrofit retrofit;
    private CompositeDisposable disposables = new CompositeDisposable();

    public PublishOnlineIntractor(){
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UrlInfo.AppUrl)
                .build();
    }

    public void AddPublish(Publishs post , AddPublishPresenter presenter) {

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.addpublish(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء نشر الخبر");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
           presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void AddMarketPublisher(MarketModel post , MarketAddPostPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.addmarket(post).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                if (data.isSuccessful() && data.body() > 0) {

                    post.id = data.body();

                    presenter.OnlineDone(post);

                }else {

                    presenter.OnOlineError("خطـأ اثناء نشر المنتج");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }


    }

    public void GetListOfHistoryPost(int id , HistoryShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.getpublishis().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void GetListOfNews(int id , NewsShowPresnter presenter){
        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.getpublishnews(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void GetListOfEvents(int id , EventShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.getpublishEvent(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void GetListOfMarkets(int id , MarketShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.getmarket(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void ConfermPost(int id , NewsShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.Confirmtions(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                boolean isconfermt = data.isSuccessful();
                if (isconfermt) {
                    presenter.OnConfermDoneOnline(data.body() , id);

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه التاكيد");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void ConfermPostForEvents(int id , EventShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.Confirmtions(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                boolean isconfermt = data.isSuccessful();
                if (isconfermt) {
                    presenter.OnConfermDoneOnline(data.body() , id);

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه التاكيد");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void ConfermPostForMarket(int id , MarketShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.ConfirmtionMarket(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
            {
                boolean isconfermt = data.isSuccessful();
                if (isconfermt) {
                    presenter.OnConfermDoneOnline(data.body() , id);

                }else {

                    presenter.OnOlineError("خطـأ اثناء عمليه التاكيد");
                }
            }, throwable -> {

                presenter.OnOlineError(throwable.getMessage());
            });

            disposables.add(creatuserobj);
        }else {
            presenter.OnOlineError("No Internet Found Please Connect To Internet");
        }

    }

    public void DeletePost(int id , NewsShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.DeletePosts(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void DeleteHistory(int id , HistoryShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.DeletePosts(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void DeletePostForEvents(int id , EventShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.DeletePosts(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void DeletePostForMarket(int id , MarketShowPresnter presenter){

        Boolean test = UrlHelpler.IsNetWorkAvailabe();
        if(test){
            PublishServices publishServices = retrofit.create(PublishServices.class);
            Disposable creatuserobj = publishServices.DeletePostsMarket(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data ->
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

    public void OnDestory(){
    retrofit = null;
        disposables.dispose();
    }
}
