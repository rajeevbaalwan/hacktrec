package in.curience.hacktrec.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.NfcTagUtils;
import in.curience.hacktrec.Utility.SharedPrefUtil;
import in.curience.hacktrec.Utility.UtilFunction;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = "Landing Activity";
    private ProgressBar progressBar;
    private SharedPrefUtil sharedPrefUtil;
    private static final int RC_NFC = 574;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sharedPrefUtil = new SharedPrefUtil(LandingActivity.this);

        showNfcStatus();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())  ){
            processNfcTag(getIntent());
        }

    }


    void processNfcTag(Intent intent){
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


        for (String techUsed : tag.getTechList()){
            Log.d(TAG,""+techUsed);
        }

        String data = NfcTagUtils.readTag(tag);

        Log.d(TAG,data);
        sharedPrefUtil.setTableID(Integer.parseInt(data.substring(11)));

        //request for menu from socket server here....


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_NFC){
            showNfcStatus();
        }
    }

    void showNfcStatus(){

        Boolean nfcState = UtilFunction.checkNFCStatus(LandingActivity.this);

        if (nfcState == null){
            // nfcInfo.setText("NFC NOT AVAILABLE");
        }

        else if (nfcState){
            //nfcInfo.setText("NFC AVAILABLE ... USE ANY ACTIVE OR PASSIVE DEVICE TO USE WITH IT...");
        }
        else {
            showSwitchOnNfcDialog();
        }
    }

    void showSwitchOnNfcDialog(){

        new AlertDialog.Builder(LandingActivity.this)
                .setMessage("NFC Is Not Enabled. In Order To Enjoy Our Services Enable Your Wifi..")
                .setCancelable(false)
                .setPositiveButton("ENABLE NFC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                            startActivityForResult(intent,RC_NFC);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivityForResult(intent,RC_NFC);
                        }
                    }
                })
                .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LandingActivity.this.finish();
                    }
                })
                .create().show();
    }


}
