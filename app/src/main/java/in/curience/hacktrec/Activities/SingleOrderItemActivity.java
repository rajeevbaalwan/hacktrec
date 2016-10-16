package in.curience.hacktrec.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class SingleOrderItemActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.single_order_item_activity);
        Spinner quantity = (Spinner) findViewById(R.id.item_quantity);
        getItemCount(quantity);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.item_quantity, android.R.layout.simple_spinner_dropdown_item);
        quantity.setAdapter(adapter);
        Button fab= (Button) findViewById(R.id.fab);

    }

    int getItemCount(Spinner quantity) {
        return (int) quantity.getSelectedItem();
    }
}