package in.curience.hacktrec.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Brekkishhh on 11-08-2016.
 */
public class UtilFunction {

    private static final String TAG = "UtilFunction";

    @Nullable
    public static Boolean checkNFCStatus(Context context){
        NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter nfcAdapter = nfcManager.getDefaultAdapter();

        if (nfcAdapter == null){
            return null;
        }

        return nfcAdapter.isEnabled();
    }

    public static void toastL(Context context,String string){
        Toast.makeText(context,string, Toast.LENGTH_LONG).show();
    }
    public static void toastS(Context context,String string){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show();
    }

    public static Boolean checkNetworkConnection(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getCurrentTime(){
        Calendar calendar  = Calendar.getInstance();
        Date date = calendar.getTime();
       // Log.d(TAG,date.toString());
        return date.toString();

    }

    public static String getIframeUrl(String videoUrl){

        return "<html><body><iframe width=\"280\" height=\"280\" src="+ videoUrl +" frameborder=\"0\" allowfullscreen></iframe></body></html>";
    }

    public static String generateBase64String(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte [] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }


    public static Typeface setNewTextStyle(Context context){
        return Typeface.createFromAsset(context.getAssets(),"Sofia-Regular.otf");
    }

    public static JSONObject postJSONObject(String completeUrl, JSONObject jsonObject)
    {
        DataOutputStream dataOutputStream;
        InputStream is;
        String jsonstring1 ="";
        JSONObject jsonObject1= null;

        JSONObject j = new JSONObject();



        try{
            String jsonstring = jsonObject.toString();
            URL url = new URL(completeUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(jsonstring.getBytes());
            dataOutputStream.flush();
            dataOutputStream.close();

            int httpResult = httpURLConnection.getResponseCode();

            if(httpResult==HttpURLConnection.HTTP_OK) {
                is = new BufferedInputStream(httpURLConnection.getInputStream());
                Scanner s = new Scanner(is).useDelimiter("\\A");
                if (s.hasNext()) {
                    jsonstring1 = s.next();
                }
            }

        }catch(MalformedURLException e){
            Log.d("error","malformedUrl in Post");
        }catch (IOException e){
            Log.d("error","IOException in Post");
        }catch(Exception e){
            e.printStackTrace();
        }


        Log.d("Error","error is:"+jsonstring1);


        return jsonObject1;
    }
}
