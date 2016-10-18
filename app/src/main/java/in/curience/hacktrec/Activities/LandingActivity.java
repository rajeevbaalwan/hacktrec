package in.curience.hacktrec.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import in.curience.hacktrec.Adapter.MenuAdapter;
import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.NfcTagUtils;
import in.curience.hacktrec.Utility.SharedPrefUtil;
import in.curience.hacktrec.Utility.UtilFunction;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = "Landing Activity";
    private ProgressBar progressBar;
    private SharedPrefUtil sharedPrefUtil;
    private static final int RC_NFC = 574;
    private Socket socket;
    private RecyclerView menuRecyclerView;
    private MenuAdapter menuAdapter;
    private LinearLayout formLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        setTitle("Menu");
        formLayout = (LinearLayout) findViewById(R.id.form);
        formLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sharedPrefUtil = new SharedPrefUtil(LandingActivity.this);
        menuRecyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(LandingActivity.this));
        menuAdapter = new MenuAdapter(LandingActivity.this,new ArrayList<MenuData>());
        menuRecyclerView.setAdapter(menuAdapter);
        showNfcStatus();
        initialiseMenuSocket();

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit(Constants.EVENT_GET_MENU,sharedPrefUtil.getTableId());
            }
        });

        socket.on(Constants.EVENT_MENU_RESPONSE, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        try{
                            menuAdapter.changeList(getMenuFromJson(((JSONObject) args[0]).getJSONArray("menu")));
                        }catch(JSONException e){
                            Log.d(TAG,"Error in json parsing from sockets......");
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (socket==null){
            initialiseMenuSocket();
        }

        if (socket!=null && !socket.connected()){
            socket.connect();
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())  && Constants.MODE==0){
            Constants.MODE =1;
            processNfcTag(getIntent());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"On Pause is called");
    }

    void processNfcTag(Intent intent){
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


        for (String techUsed : tag.getTechList()){
            Log.d(TAG,""+techUsed);
        }

        String data = NfcTagUtils.readTag(tag);

        Log.d(TAG,data);
        sharedPrefUtil.setTableID(Integer.parseInt(data.substring(11,12)));

        if(socket==null) {
            initialiseMenuSocket();
        }

        if (socket!=null && !socket.connected()){
            socket.connect();
        }


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

    void initialiseMenuSocket(){

        try{
            socket = IO.socket(Constants.SOCKETS_SERVER);
            socket.connect();
            Log.d(TAG,"Connecting to Menu Socket Server");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }

        if (!socket.connected()){
            socket.connect();
        }

    }

    private List<MenuData> getMenuFromJson(JSONArray jsonArray) throws JSONException{

        List<MenuData> datas = new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String imageUrl = jsonObject.getString("imageurl");
            String name = jsonObject.getString("name");
            String type = jsonObject.getString("type");
            String price = jsonObject.getString("price");
            int id = jsonObject.getInt("id");

            datas.add(new MenuData(id,imageUrl,name,type,price));
        }

        return datas;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landng,menu);
        MenuItem item = menu.getItem(0);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.startChat:
                Intent intent = new Intent(LandingActivity.this,ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.myorders:
                Intent intent1 = new Intent(LandingActivity.this,OrdersActivity.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startChat(View v){
        Intent intent = new Intent(LandingActivity.this,ChatActivity.class);
        startActivity(intent);
    }
}
