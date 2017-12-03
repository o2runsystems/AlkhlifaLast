package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Reports;

public class ReportsShowPresnter {

    ReportsShowReqirdView _view;

    MangWotrkOnlineIntractor _intractor;

    public ReportsShowPresnter(ReportsShowReqirdView view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();
    }

    public void GetListReport(){
        _intractor.GetLiostOfReports(this);
    }

    public void DeleteReport(int id){
        _intractor.DeleteReports(id , this);
    }

    public void OnlineDone(List<Reports> body) {
        _view.FillRecylerView(body);
    }

    public void OnOlineError(String s) {
        _view.ShowToast(s);
    }

    public void OndeleteOnline() {
        _view.DeleteitemFromRecyclerView();
    }

    public void OnDestory(){
        _intractor.OnDestory();

        _view = null;
    }
}
