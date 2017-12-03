package MangWorkFeature.Presenters;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Famous;

/**
 * Created by dev on 7/12/2017.
 */

public class AddFamousePresnter {
    AddFamousReqirdView _view;
    MangWotrkOnlineIntractor _intractor;

    public AddFamousePresnter(AddFamousReqirdView view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();

    }

    public void AddFamouse(Famous post){
        _intractor.AddFamouse(post , this);
    }

    public void OnOlineError(String s) {
        _view.ShowTost(s);

    }

    public void OnlineDone(Famous post) {
      _view.CloseActivity();
    }


    public void Ondestroy(){
        _intractor.OnDestory();
        _view = null;
        _intractor = null;
    }
}
