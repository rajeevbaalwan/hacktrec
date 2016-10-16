package in.curience.hacktrec.Db;

import android.provider.BaseColumns;

/**
 * Created by Brekkishhh on 23-08-2016.
 */
public class DbUtils {

    private static final String TYPE_TEXT = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String CREATE_TABLE = "CREATE TABLE " +
            DbSchema.Schema.TABLE_NAME +" ("+
            DbSchema.Schema._ID +"INTEGER PRIMARY KEY,"+
            DbSchema.Schema.COLUMN_MESSAGE_TEXT+ TYPE_TEXT + COMMA_SEP+
            DbSchema.Schema.COLUMN_IS_SENDED +TYPE_TEXT+COMMA_SEP+
            DbSchema.Schema.COLUMN_TIME_STAMP+TYPE_TEXT+COMMA_SEP+
            DbSchema.Schema.COLUMN_MESSAGE_TYPE +TYPE_TEXT+")";



}
