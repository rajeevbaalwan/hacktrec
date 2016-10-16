package in.curience.hacktrec.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Brekkishhh on 16-10-2016.
 */
public class SharedPrefUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private final String TABLE_ID = "table_id";

    public SharedPrefUtil(Context context) {
        this.context = context;
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
}
