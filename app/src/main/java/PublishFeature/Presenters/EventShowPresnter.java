package PublishFeature.Presenters;

import java.util.List;

import PublishFeature.ModelOprations.PublishOnlineIntractor;
import PublishFeature.PojoClasses.Publishs;

/**
 * Created by dev on 7/8/2017.
 */
@SuppressWarnings("all")
public class EventShowPresnter {

    EventShowViewReqired _view;
    PublishOnlineIntractor _intractor;

    public EventShowPresnter (EventShowViewReqired view){
        _view = view;
        _intractor = new PublishOnlineIntractor();
    }

    public void OfflineDone(){

    }

    public void GetHistoryList(int id){

        _intractor.GetListOfEvents(id , this);
    }

    public void OnlineDone(List<Publishs> historylist){

        _view.FillRecyclerView(historylist);

    }

    public void OnofflineError(String msg){

    }

    public void OnOlineError(String msg){

        _view.ShowToast(msg);
    }

    public void ConfermPost(int id) {
    _intractor.ConfermPostForEvents(id , this);
    }

    public void DeletePost(int id) {
        _intractor.DeletePostForEvents(id , this);

    }



    public void Ondestroy(){
        _intractor.OnDestory();
        _intractor = null;
        _view = null;

    }


    public void OnConfermDoneOnline(Boolean body, int id) {
        _view.WorkWithRealm(body , id );

    }


    public void OndeleteOnline() {
        _view.ShowToast("تم الحذف بنجاح");
        _view.DeleteCard();
    }


}
