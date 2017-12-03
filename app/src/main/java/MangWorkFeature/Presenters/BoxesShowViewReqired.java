package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.PojoClasses.Boxes;

/**
 * Created by dev on 7/12/2017.
 */

public interface BoxesShowViewReqired {
    void ShowToast(String msg);
    void FillRecylerView(List<Boxes> boxes);

    void DeleteitemFromRecyclerView();
}
