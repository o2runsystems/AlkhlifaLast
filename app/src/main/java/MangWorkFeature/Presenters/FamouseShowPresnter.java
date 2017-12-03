package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Famous;
import PublishFeature.PojoClasses.Publishs;

public class FamouseShowPresnter {
    FamousShowViewReqired _view;

    MangWotrkOnlineIntractor _intractor;

    public FamouseShowPresnter(FamousShowViewReqired view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();
    }

    public void GetListFamouse(){
        _intractor.GetLiostOfFamouse(this);
    }

    public void DeleteFamous(int id){
        _intractor.DeleteFamous(id , this);
    }

    public void OnlineDone(List<Famous> body) {
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
