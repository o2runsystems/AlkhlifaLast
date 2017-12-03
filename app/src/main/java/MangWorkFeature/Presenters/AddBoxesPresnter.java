package MangWorkFeature.Presenters;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import MangWorkFeature.PojoClasses.Boxes;

/**
 * Created by dev on 7/12/2017.
 */

public class AddBoxesPresnter {

    AddBoxesReqiredView _view;
    MangWotrkOnlineIntractor _intractor ;

    public AddBoxesPresnter(AddBoxesReqiredView view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();

    }

    public void AddBoxes(Boxes box){
       _intractor.AddBoxes(box , this);
    }

    public void OnlineDone(Boxes post) {
     _view.CloseActivity();
    }

    public void OnOlineError(String s) {
    _view.ShowToast(s);
    }

    public void Ondestroy(){
        _view = null;
        _intractor.OnDestory();
    }
}
