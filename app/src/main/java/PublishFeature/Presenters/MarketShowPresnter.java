package PublishFeature.Presenters;


import java.util.List;

import PublishFeature.ModelOprations.PublishOnlineIntractor;
import PublishFeature.PojoClasses.MarketModel;

@SuppressWarnings("all")
public class MarketShowPresnter {

    MarketShowViewReqired _view;
    PublishOnlineIntractor _intractor;

    public MarketShowPresnter (MarketShowViewReqired view){
        _view = view;
        _intractor = new PublishOnlineIntractor();
    }

    public void OfflineDone(){

    }

    public void GetMarketList(int id){

        _intractor.GetListOfMarkets(id , this);
    }

    public void OnlineDone(List<MarketModel> historylist){

        _view.FillRecyclerView(historylist);

    }

    public void OnofflineError(String msg){

    }

    public void OnOlineError(String msg){
   _view.ShowToast(msg);
    }

    public void Ondestroy(){
   _intractor.OnDestory();
        _intractor = null;
        _view = null;
    }

    public void ConfermPost(int id) {
        _intractor.ConfermPostForMarket(id, this);
    }
    public void DeletePost(int id) {
        _intractor.DeletePostForMarket(id , this);

    }

    public void OnConfermDoneOnline(Boolean body, int id) {
      _view.WorkWithRealm(body , id);
    }

    public void OndeleteOnline() {
        _view.ShowToast("تم الحذف بنجاح");
        _view.DeleteCard();

    }
}
