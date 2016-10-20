package in.curience.hacktrec.NetworkRequests;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * Created by Brekkishhh on 20-10-2016.
 */
public class SendLocationRequest {

    private OkHttpClient httpClient;
    private static final String TAG = "SendLocationRequest";

    public SendLocationRequest() {
        this.httpClient =  new OkHttpClient();
    }

    public void sendRequestToApi(final int pos, final MessageReceived messageReceived){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key",Constants.ZOMATO_API_KEY);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String url = "https://developers.zomato.com/api/v2.1/geocode?lat=28.6274469&lon=77.4436521";
        RequestBody body = RequestBody.create(Constants.TYPE_JSON,jsonObject.toString());


        Request request = new Request.Builder()
                .url("https://developers.zomato.com/api/v2.1/geocode?lat=28.6274469&lon=77.4436521")
                .get()
                .addHeader("user_key",Constants.ZOMATO_API_KEY)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                messageReceived.onMessageReceived(new ChatMessage("Unable to fetch message...bot is currently down", UtilFunction.getCurrentTime(), Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT));
            }

            @Override
            public void onResponse(Response response) throws IOException {

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response.body().charStream()).getAsJsonObject();


                    messageReceived.onMessageReceived(new ChatMessage(convertToJson(pos,jsonObject), UtilFunction.getCurrentTime(), Constants.IS_RECEIVED, Constants.TYPE_MESSAGE_TEXT));

            }
        });
    }

    private String convertToJson(int i,JsonObject jsonObject) {

        String toPrint = "";
        if(i==0){

            JsonArray jsonArray = jsonObject.getAsJsonArray("nearby_restaurants");
            Log.d(TAG,jsonArray.toString());

            String name;
            String address;
            String rating;
            String cuisine;

            for(int k=0;k<4;k++){
                JsonObject object = jsonArray.getAsJsonArray().get(k).getAsJsonObject();
                JsonObject  obj = object.getAsJsonObject("restaurant");

                name = obj.get("name").getAsString();
                address = obj.get("location").getAsJsonObject().get("address").getAsString() + " "+obj.get("location").getAsJsonObject().get("city").getAsString();
                cuisine = obj.get("cuisines").getAsString();
                rating = obj.get("user_rating").getAsJsonObject().get("aggregate_rating").getAsString();

                toPrint += "Name : "+name+"\n Cuisine : " +cuisine +"\n Rating : "+rating+"\n Address : "+address+"\n\n\n";

            }
        }else{
            JsonArray jsonArray = jsonObject.getAsJsonArray("nearby_restaurants");
            Log.d(TAG,jsonArray.toString());

            String name;
            String address;
            String rating;
            String cuisine;

            for(int j=4;j<8;j++){
                JsonObject object = jsonArray.getAsJsonArray().get(j).getAsJsonObject();
                JsonObject  obj = object.getAsJsonObject("restaurant");

                name = obj.get("name").getAsString();
                address = obj.get("location").getAsJsonObject().get("address").getAsString() + " "+obj.get("location").getAsJsonObject().get("city").getAsString();
                cuisine = obj.get("cuisines").getAsString();
                rating = obj.get("user_rating").getAsJsonObject().get("aggregate_rating").getAsString();

                toPrint += "Name : "+name+"\n Cuisine : " +cuisine +"\n Rating : "+rating+"\n Address : "+address+"\n\n\n";

            }
        }




        return toPrint;
    }
}
