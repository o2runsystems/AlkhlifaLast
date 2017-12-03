package PublishFeature.Presenters;

import java.util.List;

import PublishFeature.ModelOprations.PublishOnlineIntractor;
import PublishFeature.PojoClasses.Publishs;

/**
 * Created by dev on 7/8/2017.
 */
@SuppressWarnings("all")
public class NewsShowPresnter {

    NewsShowViewReqires _view;
    PublishOnlineIntractor _intractor;

    public NewsShowPresnter (NewsShowViewReqires view){
        _view = view;
        _intractor = new PublishOnlineIntractor();
    }

    public void OfflineDone(){

    }

    public void GetListOfNews(int id){

        _intractor.GetListOfNews(id , this);
    }

    public void ConfermPost(int id){
        _intractor.ConfermPost(id, this);
    }

    public void DeletePost(int id){
        _intractor.DeletePost(id , this);
    }

    public void OnlineDone(List<Publishs> historylist){

        _view.FillRecyclerView(historylist);

    }

    public void OnConfermDoneOnline(boolean state , int id){

        _view.WorkWithRealm(state , id);

    }

    public void OndeleteOnline(){
        _view.ShowToast("تم الحذف بنجاح");
        _view.DeleteCard();
    }

    public void OnofflineError(String msg){
     _view.ShowToast(msg);
    }

    public void OnOlineError(String msg){

    }

    public void Ondestroy(){
   _intractor.OnDestory();
        _intractor = null;
        _view = null;
    }

}
