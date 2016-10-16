package in.curience.hacktrec.Models;

import java.io.Serializable;

/**
 * Created by Brekkishhh on 23-08-2016.
 */
public class ChatMessage implements Serializable {

    private String message;
    private String time;
    private String isSended;
    private String typeOfMessage;

    public ChatMessage(String message, String time,String isSended,String typeOfMessage) {
        this.message = message;
        this.time = time;
        this.isSended = isSended;
        this.typeOfMessage = typeOfMessage;
    }


    public String getMessage() {
        return this.message;
    }

    public String getTime() {
        return this.time;
    }

    public String isSended() {
        return this.isSended;
    }

    public String getTypeOfMessage() {
        return this.typeOfMessage;
    }

}
