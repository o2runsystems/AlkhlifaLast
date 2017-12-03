package PublishFeature.Presenters;


import java.util.List;

import PublishFeature.PojoClasses.Publishs;

@SuppressWarnings("all")
public interface NewsShowViewReqires {
  void FillRecyclerView(List<Publishs> list);
  void ShowToast(String msg);
    void WorkWithRealm(boolean state , int id);
  void DeleteCard();
}
