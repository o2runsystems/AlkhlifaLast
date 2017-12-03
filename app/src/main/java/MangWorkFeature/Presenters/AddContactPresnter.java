package MangWorkFeature.Presenters;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import WebServices.Models.Contact;
public class AddContactPresnter {


    MangWotrkOnlineIntractor _intractor ;

    AddContactViewReqird _view;


    public AddContactPresnter(AddContactViewReqird view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();

    }

    public void AddContact(Contact contact){
        _intractor.AddContact(contact , this);
    }

    public void OnlineDone(Contact post) {
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
