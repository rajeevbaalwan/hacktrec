package in.curience.hacktrec.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import in.curience.hacktrec.Models.ChatMessage;


/**
 * Created by Brekkishhh on 23-08-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Messages.db";

    public DbHelper (Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbUtils.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMessageToDb(ChatMessage message){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues entries = new ContentValues();
        entries.put(DbSchema.Schema.COLUMN_MESSAGE_TEXT,message.getMessage());
        entries.put(DbSchema.Schema.COLUMN_IS_SENDED,message.isSended());
        entries.put(DbSchema.Schema.COLUMN_TIME_STAMP,message.getTime());
        entries.put(DbSchema.Schema.COLUMN_MESSAGE_TYPE,message.getTypeOfMessage());

        db.insert(DbSchema.Schema.TABLE_NAME,null,entries);
    }

    public List<ChatMessage> getCompleteChat(){

        List<ChatMessage> chatList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String [] projection = {DbSchema.Schema.COLUMN_MESSAGE_TEXT, DbSchema.Schema.COLUMN_TIME_STAMP,DbSchema.Schema.COLUMN_IS_SENDED,DbSchema.Schema.COLUMN_MESSAGE_TYPE};

        Cursor readCursor = db.query(DbSchema.Schema.TABLE_NAME,projection,null,null,null,null,null);

        readCursor.moveToFirst();
        int totalRows = readCursor.getCount();

        while (totalRows>0){
            totalRows--;
            String messageText = readCursor.getString(readCursor.getColumnIndexOrThrow(DbSchema.Schema.COLUMN_MESSAGE_TEXT));
            String isSender = readCursor.getString(readCursor.getColumnIndexOrThrow(DbSchema.Schema.COLUMN_IS_SENDED));
            String timeStamp = readCursor.getString(readCursor.getColumnIndexOrThrow(DbSchema.Schema.COLUMN_TIME_STAMP));
            String messageType = readCursor.getString(readCursor.getColumnIndexOrThrow(DbSchema.Schema.COLUMN_MESSAGE_TYPE));

            chatList.add(new ChatMessage(messageText,timeStamp,isSender,messageType));
            readCursor.moveToNext();
        }

        readCursor.close();
        return chatList;
    }
}
