package in.curience.hacktrec.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

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
        Log.d(TAG,date.toString());
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
}
