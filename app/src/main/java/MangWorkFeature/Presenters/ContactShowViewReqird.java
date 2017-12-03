package MangWorkFeature.Presenters;

import WebServices.Models.Contact;


public interface ContactShowViewReqird {
    void ShowInView(Contact contact);

    void ShowToast(String s);
}
