package in.curience.hacktrec.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.Models.OrderedData;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.SharedPrefUtil;
import in.curience.hacktrec.Utility.UtilFunction;

public class SingleItem extends AppCompatActivity {

    private int quantitySelected = 1;
    private Spinner quantity;
    private Button giveOrderButton;
    private TextView itemPrice;
    private EditText extraNeeds;
    private TextView itemDescription;
    private Socket socket;
    private static final String TAG = "Single Item Act";
    private SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        final MenuData item = (MenuData) getIntent().getSerializableExtra("details");
        setTitle(item.getItemName());

        itemPrice = (TextView) findViewById(R.id.item_price);
        itemDescription = (TextView) findViewById(R.id.item_description);
        sharedPrefUtil = new SharedPrefUtil(SingleItem.this);
        extraNeeds = (EditText)  findViewById(R.id.extraDemandsEditText);
        itemPrice.setText(item.getItemPrice());
        itemDescription.setText(Html.fromHtml("<b>"+item.getItemName()+"</b> <br>   "+item.getItemType()));

        initialiseOrderSocket();

        quantity = (Spinner) findViewById(R.id.item_quantity);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
        quantity.setAdapter(adapter);

        quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantitySelected = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       giveOrderButton = (Button) findViewById(R.id.fab);

        giveOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefUtil.putOrderList(new OrderedData(item.getItemName(),String.valueOf(quantitySelected),item.getItemPrice()));

                if (!socket.connected()){
                    socket.connect();
                }
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("id",item.getId());
                    jsonObject.put("itemname",item.getItemName());
                    jsonObject.put("quantity",quantitySelected);
                    jsonObject.put("tableid",sharedPrefUtil.getTableId());
                    jsonObject.put("extras",extraNeeds.getText().toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                Log.d(TAG,"sending order");
                extraNeeds.setText("");
                socket.emit(Constants.EVENT_SEND_ORDER,jsonObject);
                UtilFunction.toastS(SingleItem.this,"Your Order is placed");
                SingleItem.this.finish();
            }
        });

    }

    void initialiseOrderSocket() {

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


