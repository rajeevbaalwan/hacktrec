package in.curience.hacktrec.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
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
    private TextView subTotal;
    private TextView tax;
    private TextView totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initialisePaymentSocket();

        subTotal = (TextView) findViewById(R.id.order_sub_total_amount);
        tax = (TextView) findViewById(R.id.order_tax);
        totalAmount = (TextView) findViewById(R.id.total_amount);
        sharedPrefUtil = new SharedPrefUtil(OrdersActivity.this);
        orderRecyclerView= (RecyclerView) findViewById(R.id.order_recycler_view);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
        orderAdapter=new OrderedItemAdapter(OrdersActivity.this,this.sharedPrefUtil.getOrdersList());
        orderRecyclerView.setAdapter(orderAdapter);
        paymentRequest = (Button) findViewById(R.id.startPaymentRequest);
        getTotalAndSetViews();
        paymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v,"Your bill is on the way...",Snackbar.LENGTH_SHORT).setAction("DONE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrdersActivity.this.finish();
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

    public void getTotalAndSetViews(){
        List<OrderedData> datas = sharedPrefUtil.getOrdersList();
        int total = 0;
        for(int i=0;i<datas.size();i++){
            total+= Integer.parseInt(datas.get(i).getItemPrice()) * Integer.parseInt(datas.get(i).getItemQuantity());
        }

        subTotal.setText(""+total);
        tax.setText(""+30);
        totalAmount.setText(""+(total+30));
    }
}
