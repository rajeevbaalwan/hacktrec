package in.curience.hacktrec.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class SingleOrderItemActivity extends AppCompatActivity{


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.single_order_item_activity);

        MenuData itemInfo = (MenuData) getIntent().getSerializableExtra("details");

        Spinner quantity= (Spinner) findViewById(R.id.item_quantity);

        //ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.item_quantity,android.R.layout.simple_spinner_dropdown_item);


    }
}
