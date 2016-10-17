package in.curience.hacktrec.Activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import in.curience.hacktrec.Adapter.OrderedItemAdapter;
import in.curience.hacktrec.Models.OrderedData;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.SharedPrefUtil;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private OrderedItemAdapter orderAdapter;
    private Socket socket;
    private static final String TAG = "OrdersActivity";
    private Button paymentRequest;
    private SharedPrefUtil sharedPrefUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initialisePaymentSocket();
        sharedPrefUtil = new SharedPrefUtil(OrdersActivity.this);
        orderRecyclerView= (RecyclerView) findViewById(R.id.order_recycler_view);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
        orderAdapter=new OrderedItemAdapter(OrdersActivity.this,getData());
        orderRecyclerView.setAdapter(orderAdapter);
        paymentRequest = (Button) findViewById(R.id.startPaymentRequest);
        paymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v,"Your bill is on the way...",Snackbar.LENGTH_SHORT).setAction("DONE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("tableid", sharedPrefUtil.getTableId());
                }catch (JSONException e){
                    e.printStackTrace();
                }
                socket.emit(Constants.EVENT_PAYMENT_REQUEST,jsonObject);
            }
        });


    }

    public List<OrderedData> getData()
    {    List<OrderedData>list = new ArrayList();
         String[] itemName=getResources().getStringArray(R.array.name);
         String[] itemAmount=getResources().getStringArray(R.array.price);
         String[] itemQuantity=getResources().getStringArray(R.array.item_quantity);
        for(int i=0;i<10;i++)
        {
          list.add(new OrderedData(itemName[i],itemQuantity[i],itemAmount[i]));

        }

        return list;
    }

    void initialisePaymentSocket() {

        try {
            socket = IO.socket(Constants.SOCKETS_SERVER);
            socket.connect();
            Log.d(TAG, "Connecting to Order Socket Server");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (!socket.connected()) {
            socket.connect();
        }
    }
}
