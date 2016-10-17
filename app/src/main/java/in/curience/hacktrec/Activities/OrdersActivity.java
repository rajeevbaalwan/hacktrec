package in.curience.hacktrec.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import in.curience.hacktrec.Adapter.OrderedItemAdapter;
import in.curience.hacktrec.Models.OrderedData;
import in.curience.hacktrec.R;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    OrderedItemAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        orderRecyclerView= (RecyclerView) findViewById(R.id.order_recycler_view);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
        orderAdapter=new OrderedItemAdapter(OrdersActivity.this,getData());
        orderRecyclerView.setAdapter(orderAdapter);

    }
    public List<OrderedData> getData()
    {    List<OrderedData>list =new ArrayList();
         String[] itemName=getResources().getStringArray(R.array.name);
         String[] itemAmount=getResources().getStringArray(R.array.price);
         String[] itemQuantity=getResources().getStringArray(R.array.item_quantity);
        for(int i=0;i<10;i++)
        {
          list.add(new OrderedData(itemName[i],itemQuantity[i],itemAmount[i]));

        }

        return list;
    }
}
