package in.curience.hacktrec.NetworkRequests;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.curience.hacktrec.Callbacks.MessageReceived;
import in.curience.hacktrec.Models.ChatMessage;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.UtilFunction;

/**
 * Created by Brekkishhh on 19-10-2016.
 */
public class SendCodeRequest {

    private OkHttpClient httpClient;
    private static final String TAG = "SendCodeRequest";

    public SendCodeRequest() {
        this.httpClient =  new OkHttpClient();
    }

    public void sendRequestToApi(String code, final MessageReceived messageReceived){

        Request request = new Request.Builder()
                .url("https://api.nutritionix.com/v1_1/item?upc="+code+"&appId="+Constants.APP_ID+"&appKey="+Constants.API_KEY)
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
                    Log.d(TAG,""+jsonObject);

                    messageReceived.onMessageReceived(new ChatMessage(convertToJson(jsonObject),UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                  /*  if (jsonObject.has("status")){



                     *//*   String data = jsonObject.getString("data");

                        if (jsonObject.getBoolean("status")){
                            String type = jsonObject.getString("type");
                            messageReceived.onMessageReceived(new ChatMessage(data, UtilFunction.getCurrentTime(), Constants.IS_RECEIVED,type));
                        }
                        else
                        {
                            messageReceived.onMessageReceived(new ChatMessage(data,  UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                        }*//*
                    }

                    else {
                        messageReceived.onMessageReceived(new ChatMessage("Check Internet Connection", UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    }*/
                }catch (JSONException e){
                    messageReceived.onMessageReceived(new ChatMessage("Problem Contacting With Bot Servers", UtilFunction.getCurrentTime(),Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
                    Log.e(TAG,e.getMessage());
                }
            }
        });
    }

    private String convertToJson(JSONObject jsonObject) throws JSONException{
        String id = jsonObject.getString("item_id");
        String item_name = jsonObject.getString("item_name");
        String brand_name = jsonObject.getString("brand_name");
        String ingredients = jsonObject.getString("nf_ingredient_statement");
        int calories = jsonObject.getInt("nf_calories");


        int carbohydrates = jsonObject.getInt("nf_total_carbohydrate");

        String fin = "";

        fin += "id : "+id +"\n"+"Name : "+item_name+"\nBrand : "+brand_name+"\n Ingredients : "+ingredients+"Calories : "+calories+"\n Carbohydrates : "+carbohydrates;

        return fin;
    }
}
