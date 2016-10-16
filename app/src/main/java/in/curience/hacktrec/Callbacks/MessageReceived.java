package in.curience.hacktrec.Callbacks;


import in.curience.hacktrec.Models.ChatMessage;

/**
 * Created by Brekkishhh on 25-08-2016.
 */
public interface MessageReceived {

    void onMessageReceived(ChatMessage message);
}
