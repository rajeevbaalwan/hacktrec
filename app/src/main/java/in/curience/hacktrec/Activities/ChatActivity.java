package in.curience.hacktrec.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import in.curience.hacktrec.Adapter.ChatAdapter;
import in.curience.hacktrec.Callbacks.MessageReceived;
import in.curience.hacktrec.Db.DbHelper;
import in.curience.hacktrec.Models.ChatMessage;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.UtilFunction;


public class ChatActivity extends AppCompatActivity implements MessageReceived {

    private static final String TAG = "ChatActivity";
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendMessageButton;
    private ImageButton sendImageButton;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DbHelper dbHelper;
    private Socket socket;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);




        try {
            socket = IO.socket(Constants.SOCKETS_SERVER);
            socket.connect();
            Log.d(TAG,"Connecting to socket server");

        } catch (URISyntaxException e) {
            Log.d(TAG,"error connecting to socket..");
            e.printStackTrace();
        }



        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG,"Connection to socket established.");
            }
        });

        socket.emit("update","hey my name is rahul");



        socket.on(Constants.EVENT_RECEIVE_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG,(String)args[0]);
                final ChatMessage message = new ChatMessage((String)args[0],"wefd",Constants.IS_RECEIVED,Constants.TYPE_MESSAGE_TEXT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatAdapter.addMessage(message);
                    }
                });
                dbHelper.addMessageToDb(message);
            }
        });


        messagesRecyclerView = (RecyclerView) findViewById(R.id.chatMessagesRecyclerView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendImageButton = (ImageButton) findViewById(R.id.sendImageButton);


        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        dbHelper = new DbHelper(ChatActivity.this);
        linearLayoutManager.setReverseLayout(true);

        chatAdapter = new ChatAdapter(ChatActivity.this,dbHelper.getCompleteChat());
        Log.d(TAG,""+dbHelper.getCompleteChat().size());
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(chatAdapter);


        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = messageEditText.getText().toString();
                messageEditText.setText(null);

                if (message.length()!=0){

                    chatAdapter.addMessage(new ChatMessage(message, UtilFunction.getCurrentTime(), Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    dbHelper.addMessageToDb(new ChatMessage(message,UtilFunction.getCurrentTime(),Constants.IS_SENDED,Constants.TYPE_MESSAGE_TEXT));
                    socket.emit(Constants.EVENT_SEND_MESSAGE,message);

                    if (!socket.connected()){
                        socket.connect();
                        Log.d(TAG,"Socket not connected");
                    }

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
