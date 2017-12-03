package WebServices.Models;

/**
 * Created by dev on 10/2/2016.
 */
public class TextChatMessage {
    public TextChatMessage(String msgbody , String msgTime , int type , String from){
        this.MessageBody = msgbody;
        this.MessageTime = msgTime;
        this.TypeAndDir = type;
        From = from;
    }
    public int TypeAndDir;
    public String MessageBody;
    public String MessageTime;
    public String From;
}
