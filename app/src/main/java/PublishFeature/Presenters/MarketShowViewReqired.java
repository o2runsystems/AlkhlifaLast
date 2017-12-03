package PublishFeature.Presenters;


import java.util.List;

import PublishFeature.PojoClasses.MarketModel;

@SuppressWarnings("all")
public interface MarketShowViewReqired {
    public void FillRecyclerView(List<MarketModel> lists);
    void ShowToast(String msg);
    void WorkWithRealm(boolean state , int id);

    void DeleteCard();
}
