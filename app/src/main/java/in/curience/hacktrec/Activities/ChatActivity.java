package in.curience.hacktrec.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;

import in.curience.hacktrec.Adapter.ChatAdapter;
import in.curience.hacktrec.Callbacks.MessageReceived;
import in.curience.hacktrec.Db.DbHelper;
import in.curience.hacktrec.Models.ChatMessage;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.UtilFunction;


public class ChatActivity extends AppCompatActivity implements MessageReceived {

    private static final String TAG = "MainActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ImageButton sendImageButton;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.chatMessagesRecyclerView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendImageButton = (ImageButton) findViewById(R.id.sendImageButton);


        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        dbHelper = new DbHelper(ChatActivity.this);
        linearLayoutManager.setReverseLayout(true);

        chatAdapter = new ChatAdapter(ChatActivity.this,dbHelper.getCompleteChat());
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(chatAdapter);


        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEditText.getText().toString();
                messageEditText.setText(null);

                if (message.length()!=0 && message.startsWith("#")){

                    chatAdapter.addMessage(new ChatMessage(message, UtilFunction.getCurrentTime(), Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    dbHelper.addMessageToDb(new ChatMessage(message,UtilFunction.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    //send message here

                }
            }
        });

        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(imageIntent,"Select Image To Send"),Constants.RQ_GALLERY_IMAGE);

            }
        });

    }

    @Override
    public void onMessageReceived(final ChatMessage message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter.addMessage(message);
            }
        });
        dbHelper.addMessageToDb(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }



}
