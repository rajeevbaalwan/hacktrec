package in.curience.hacktrec.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.curience.hacktrec.Models.OrderedData;

/**
 * Created by Brekkishhh on 16-10-2016.
 */
public class SharedPrefUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private final String TABLE_ID = "table_id";
    private final String FILE_NAME = "shared_pref";
    private final String ORDERS_LIST = "orders_list";
    private Gson gson;
    private ArrayList<OrderedData> orderedDatas;
    private String JSON_ITEM_LIST;

    public SharedPrefUtil(Context context) {

        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.gson = new Gson();
        orderedDatas = new ArrayList<>();
        JSON_ITEM_LIST = gson.toJson(orderedDatas);

        if (!sharedPreferences.contains(ORDERS_LIST)){
            editor.putString(ORDERS_LIST,JSON_ITEM_LIST).commit();

        }

    }

    public void setTableID(int tableID){
        editor.putInt(TABLE_ID,tableID).commit();
    }

    public int getTableId(){
        if (sharedPreferences.contains(TABLE_ID)){
            return sharedPreferences.getInt(TABLE_ID,0);
        }
        return -1;
    }

    public void putOrderList(OrderedData data){
        ArrayList<OrderedData> list = getOrdersList();
        list.add(data);
        String updated = gson.toJson(list);
        editor.putString(ORDERS_LIST,updated).commit();

    }

    public ArrayList<OrderedData> getOrdersList(){
        String json = sharedPreferences.getString(ORDERS_LIST,"");
        Type type = new TypeToken<ArrayList<OrderedData>>(){}.getType();
        ArrayList<OrderedData> list = gson.fromJson(json,type);
        return list;
    }

    public void clearData(){
        editor.clear().commit();
    }



}
