package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.PojoClasses.Famous;
import MangWorkFeature.PojoClasses.Reports;

/**
 * Created by dev on 7/18/2017.
 */

public interface ReportsShowReqirdView {

    void FillRecylerView(List<Reports> body);

    void ShowToast(String s);

    void DeleteitemFromRecyclerView();
}
