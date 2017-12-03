package PublishFeature.Presenters;


import PublishFeature.ModelOprations.PublishOnlineIntractor;
import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.UrlInfo;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

@SuppressWarnings("all")
public class AddPublishPresenter {

    AddPublisherReqirdView _viewreqird;
    PublishOnlineIntractor _onlineintractor;

    HubConnection conction;
    HubProxy proxy;

    public AddPublishPresenter(AddPublisherReqirdView view){
        _viewreqird = view;
        _onlineintractor = new PublishOnlineIntractor();
        Singalrconction();
    }

    public void AddPublisher(Publishs post){
        _onlineintractor.AddPublish(post , this);
    }

    public void OfflineDone(){

    }

    public void OnlineDone(Publishs post){
        if(post.Type == 1){
            proxy.invoke("SendBroadCastCardForNews" , post);
            _viewreqird.CloseActivity();

        }
        else if(post.Type ==2){

            proxy.invoke("SendBroadCastCardForevents" , post);
            _viewreqird.CloseActivity();
        }else if(post.Type ==3){
            _viewreqird.CloseActivity();
        }


    }

    public void OnofflineError(String msg){
        _viewreqird.ShowTost(msg);

    }

    public void OnOlineError(String msg){
        _viewreqird.ShowTost(msg);
    }

    void Singalrconction(){
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        String Url = UrlInfo.ChatHubUrls;
        conction = new HubConnection(Url);
        proxy = conction.createHubProxy("card");
        conction.start();
    }

    public void Ondestroy(){
        try {
            conction.stop();
            _onlineintractor.OnDestory();
            _onlineintractor = null ;
            _viewreqird = null;
            proxy = null;

        }catch (Exception e){}

    }

}
