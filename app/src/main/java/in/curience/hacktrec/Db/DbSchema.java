package in.curience.hacktrec.Db;

import android.provider.BaseColumns;

/**
 * Created by Brekkishhh on 23-08-2016.
 */
public class DbSchema {


    public DbSchema() {
    }

    public static abstract class Schema implements BaseColumns {

        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_IS_SENDED = "is_sended";
        public static final String COLUMN_TIME_STAMP = "time_stamp";
        public static final String COLUMN_MESSAGE_TYPE = "type_message";

    }
}
