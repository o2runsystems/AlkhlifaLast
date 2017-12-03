package MangWorkFeature.Presenters;

import MangWorkFeature.ModelOprations.MangWotrkOnlineIntractor;
import WebServices.Models.Contact;

public class ContactShowPresnter {
    ContactShowViewReqird _view;

    MangWotrkOnlineIntractor _intractor;

    public ContactShowPresnter(ContactShowViewReqird view){
        _view = view;
        _intractor = new MangWotrkOnlineIntractor();
    }

    public void GetContact(){
        _intractor.GetContact(this);
    }

    public void OnlineDone(Contact contact) {
        _view.ShowInView(contact);
    }

    public void OnOlineError(String s) {
        _view.ShowToast(s);
    }



    public void Ondestory(){
        _intractor.OnDestory();
        _intractor = null;
        _view = null;

    }
}
