package in.curience.hacktrec.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.util.Collections;
import java.util.List;

import in.curience.hacktrec.Models.ChatMessage;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.Constants;
import in.curience.hacktrec.Utility.UtilFunction;


/**
 * Created by Brekkishhh on 24-08-2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> messages;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;

    public ChatAdapter(Context context,List<ChatMessage> messages) {
        Collections.reverse(messages);
        this.messages = messages;
        this.context = context;
        this.imageLoader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(android.R.drawable.alert_dark_frame)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        initImageLoader(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SendChatViewHolder){

            TextView messageTextView = ((SendChatViewHolder) holder).messageTextView;
            ImageView messageImageView = ((SendChatViewHolder) holder).messageImageView;
            WebView messageVideoView = ((SendChatViewHolder) holder).messageVideoView;

            messageTextView.setVisibility(View.GONE);
            messageVideoView.setVisibility(View.GONE);
            messageImageView.setVisibility(View.GONE);

            messageVideoView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            WebSettings webSettings = messageVideoView.getSettings();
            webSettings.setJavaScriptEnabled(true);



            if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_TEXT)){
                messageTextView.setVisibility(View.VISIBLE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.GONE);
                messageTextView.setText(messages.get(position).getMessage());
            }

            else if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_IMAGE)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.VISIBLE);
                messageVideoView.setVisibility(View.GONE);

                imageLoader.displayImage(messages.get(position).getMessage(),messageImageView, options);


            }

            else if ( messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_VIDEO)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.VISIBLE);
                messageVideoView.loadData(UtilFunction.getIframeUrl("https://www.youtube.com/embed/"+messages.get(position).getMessage()), "text/html", "utf-8");
            }

        }else if (holder instanceof ReceivedChatViewHolder){

            TextView messageTextView = ((ReceivedChatViewHolder) holder).messageTextView;
            ImageView messageImageView = ((ReceivedChatViewHolder) holder).messageImageView;
            WebView messageVideoView = ((ReceivedChatViewHolder) holder).messageVideoView;
            messageVideoView.setVisibility(View.GONE);
            messageImageView.setVisibility(View.GONE);
            messageTextView.setVisibility(View.GONE);

            messageVideoView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });

            WebSettings webSettings = messageVideoView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_TEXT)){
                messageTextView.setVisibility(View.VISIBLE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.GONE);
                messageTextView.setText(messages.get(position).getMessage());

            }

            else if (messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_IMAGE)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.VISIBLE);
                messageVideoView.setVisibility(View.GONE);


                imageLoader.displayImage(messages.get(position).getMessage(),messageImageView, options);
            }

            else if ( messages.get(position).getTypeOfMessage().equals(Constants.TYPE_MESSAGE_VIDEO)){
                messageTextView.setVisibility(View.GONE);
                messageImageView.setVisibility(View.GONE);
                messageVideoView.setVisibility(View.VISIBLE);
                messageVideoView.loadData(UtilFunction.getIframeUrl("https://www.youtube.com/embed/"+messages.get(position).getMessage()), "text/html", "utf-8");
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (messages.get(position).isSended().equals(Constants.IS_SENDED)){
            return Constants.SENDER;
        }
        return Constants.RECEIVED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.SENDER){
            return new SendChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message,parent,false));
        }

        else if (viewType == Constants.RECEIVED){
            return new ReceivedChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recieved_message,parent,false));
        }

        return null;
    }

    protected class SendChatViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;
        private ImageView messageImageView;
        private WebView messageVideoView;


        public SendChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.sendMessageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.sendMessageImageView);
            messageVideoView = (WebView) itemView.findViewById(R.id.sendMessageVideoView);

        }
    }

    protected class ReceivedChatViewHolder extends RecyclerView.ViewHolder{

        private TextView messageTextView;
        private ImageView messageImageView;
        private WebView messageVideoView;


        public ReceivedChatViewHolder(View itemView) {
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.receivedMessageTextView);
            messageImageView = (ImageView) itemView.findViewById(R.id.receivedMessageImageView);
            messageVideoView = (WebView) itemView.findViewById(R.id.receivedMessageVideoView);
        }
    }


    public void addMessage(ChatMessage message){
        Collections.reverse(messages);
        messages.add(message);
        Collections.reverse(messages);
        this.notifyDataSetChanged();
    }

    public void changeChatList(List<ChatMessage> messages){
        Collections.reverse(messages);
        this.messages  = messages;
        this.notifyDataSetChanged();
    }

    public int getChatListSize(){
        return this.messages.size();
    }

    public void initImageLoader(Context context){

        File cacheDir;
        ImageLoaderConfiguration config;
        DisplayImageOptions options;


        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"JunkFolder");}
        else{
            cacheDir=context.getCacheDir();}
        if(!cacheDir.exists()){
            cacheDir.mkdirs();}

        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.abc_textfield_activated_mtrl_alpha)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .build();

        imageLoader.init(config);
    }

}
