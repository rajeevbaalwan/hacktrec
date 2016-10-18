package in.curience.hacktrec.NetworkRequests;

import android.util.Log;

import com.squareup.okhttp.Call;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.curience.hacktrec.Callbacks.MessageReceived;

import in.curience.hacktrec.Models.ChatMessage;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.UtilFunction;


/**
 * Created by Brekkishhh on 25-08-2016.
 */
public class SendMessage {

    private OkHttpClient httpClient;
    private static final String TAG = "SendMessage";

    public SendMessage() {
        this.httpClient =  new OkHttpClient();
    }

    public void sendMessageToBot(final String messageBody, final MessageReceived messageReceived){

        Request request = new Request.Builder()
                .url("http://drivesmart.herokuapp.com/guidebot?q="+messageBody.substring(1))
                .get()
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down", UtilFunction.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.has("status")){

                        String data = jsonObject.getString("data");

                        if (jsonObject.getBoolean("status")){
                            String type = jsonObject.getString("type");
                            messageReceived.onMessageReceived(new ChatMessage(data, UtilFunction.getCurrentTime(), Constants.IS_RECEIVED,type));
                        }
                        else
                        {
                            messageReceived.onMessageReceived(new ChatMessage(data,  UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                        }
                    }

                    else {
                        messageReceived.onMessageReceived(new ChatMessage("Check Internet Connection", UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    }
                }catch (JSONException e){
                    messageReceived.onMessageReceived(new ChatMessage("Problem Contacting With Bot Servers", UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    Log.e(TAG,e.getMessage());
                }
            }
        });
            }


          /* */


    public void sendImageToBot(String base64Image, final MessageReceived messageReceived){

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("q",base64Image);
        }catch (JSONException ex){
            ex.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(Constants.TYPE_JSON,jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://drivesmart.herokuapp.com/guidebot")
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request1, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down",UtilFunction.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse( Response response) throws IOException {

                String responseString = response.body().string();
                Log.d(TAG,responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.has("status")){

                        String data = jsonObject.getString("data");

                        if (jsonObject.getBoolean("status")){
                            String type = jsonObject.getString("type");
                            messageReceived.onMessageReceived(new ChatMessage(data, UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,type));
                        }
                        else
                        {
                            messageReceived.onMessageReceived(new ChatMessage(data,  UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                        }
                    }

                    else {
                        messageReceived.onMessageReceived(new ChatMessage("Check Internet Connection", UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    }


                }catch (JSONException e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
    }
}
