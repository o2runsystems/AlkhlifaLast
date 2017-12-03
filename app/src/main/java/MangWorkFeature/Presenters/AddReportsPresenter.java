package MangWorkFeature.Presenters;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Reports;


public class AddReportsPresenter {

    AddReportsReqiredView _view;
    MangWotrkOnlineIntractor _intractor;

    public AddReportsPresenter(AddReportsReqiredView view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();

    }

    public void AddReport(Reports post){
        _intractor.AddReport(post , this);
    }

    public void OnOlineError(String s) {
        _view.ShowTost(s);

    }

    public void OnlineDone(Reports post) {
        _view.CloseActivity();
    }


    public void Ondestroy(){
        _intractor.OnDestory();
        _view = null;
        _intractor = null;
    }
}
