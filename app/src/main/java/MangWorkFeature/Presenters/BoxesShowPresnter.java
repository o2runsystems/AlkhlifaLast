package MangWorkFeature.Presenters;

import java.util.List;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Boxes;

/**
 * Created by dev on 7/12/2017.
 */

public class BoxesShowPresnter {

    BoxesShowViewReqired _view;

    MangWotrkOnlineIntractor _intractor;

    public BoxesShowPresnter(BoxesShowViewReqired view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();
    }

    public void GetListOfBoxes(){
      _intractor.GetLiostOfBoxes(this);
    }

    public void DeleteBox(int id){
        _intractor.DeleteBoxes(id , this);
    }

    public void OnlineDone(List<Boxes> body) {
   _view.FillRecylerView(body);
    }

    public void OnOlineError(String s) {
      _view.ShowToast(s);
    }

    public void OndeleteOnline() {
     _view.DeleteitemFromRecyclerView();
    }

    public void Ondestory(){
        _intractor.OnDestory();
        _intractor = null;
        _view = null;

    }
}
