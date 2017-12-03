package PublishFeature.Presenters;

import java.util.List;

import PublishFeature.PojoClasses.Publishs;

/**
 * Created by dev on 7/8/2017.
 */
@SuppressWarnings("all")
public interface HistoryShowViewReqires {
    void FillRecyclerView(List<Publishs> publishsList);
    void ShowToast(String msg);

    void DeleteCard();
}
