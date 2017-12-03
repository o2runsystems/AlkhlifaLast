package PublishFeature.Presenters;

import PublishFeature.ModelOprations.PublishOnlineIntractor;
import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.UrlInfo;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

/**
 * Created by dev on 7/8/2017.
 */
@SuppressWarnings("all")
public class MarketAddPostPresnter {
    MarketAddPostViewReqired _viewreqird;
    PublishOnlineIntractor _onlineintractor;

    HubConnection conction;
    HubProxy proxy;


    public MarketAddPostPresnter(MarketAddPostViewReqired view){
        _viewreqird = view;
        _onlineintractor = new PublishOnlineIntractor();
        Singalrconction();
    }

    public void AddMraketPost(MarketModel post){
        _onlineintractor.AddMarketPublisher(post , this);
    }

    public void OfflineDone(){

    }

    public void OnlineDone(MarketModel post){
        proxy.invoke("SendBroadCastCardFormarket" , post);
        _viewreqird.CloseActivity();
    }

    public void OnofflineError(String msg){

    }

    public void OnOlineError(String msg){
        _viewreqird.ShowToast(msg);

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
            _onlineintractor = null;
            _viewreqird = null;
        }catch (Exception e){}

    }

}
