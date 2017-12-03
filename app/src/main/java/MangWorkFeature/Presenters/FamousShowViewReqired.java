package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.PojoClasses.Famous;

/**
 * Created by dev on 7/12/2017.
 */

public interface FamousShowViewReqired {
    void ShowToast(String msg);

    void FillRecylerView(List<Famous> data);

    void DeleteitemFromRecyclerView();

}
