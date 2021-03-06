package in.curience.hacktrec.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.List;

import in.curience.hacktrec.Activities.SingleItem;
import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.R;
import in.curience.hacktrec.Utility.UtilFunction;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {

    private List<MenuData> data;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public  MenuAdapter(Context context,List<MenuData> data) {
        this.data=data;
        this.context=context;

        this.imageLoader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(android.R.drawable.alert_dark_frame)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new RoundedBitmapDisplayer(10))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        initImageLoader(context);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.progressBar.setVisibility(View.GONE);
        holder.itemPrice.setVisibility(View.VISIBLE);

        holder.itemName.setText(data.get(position).getItemName());
        holder.itemName.setTypeface(UtilFunction.setNewTextStyle(context));
        holder.itemPrice.setText("₹ "+data.get(position).getItemPrice());
        holder.itemType.setText(data.get(position).getItemType());
        holder.ratingText.setText("Rating :"+data.get(position).getItemRating());
        holder.cookingTime.setText("Cooking Time :"+data.get(position).getAvgTime());
        holder.itemPic.setImageBitmap(null);
        imageLoader.displayImage(data.get(position).getImageUrl(), holder.itemPic, options);
        final int pos = position;
        holder.clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), SingleItem.class);
                intent.putExtra("details",data.get(pos));
                intent.putExtra("pos",pos);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemPic;
        private TextView itemName;
        private TextView itemType;
        private TextView cookingTime;
        private TextView itemPrice;
        private LinearLayout clickableLayout;
        private ProgressBar progressBar;
        private TextView ratingText;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemName= (TextView) itemView.findViewById(R.id.item_name);
            itemPic= (ImageView) itemView.findViewById(R.id.item_pic);
            itemType= (TextView) itemView.findViewById(R.id.item_type);
            itemPrice= (TextView) itemView.findViewById(R.id.item_price);
            clickableLayout = (LinearLayout) itemView.findViewById(R.id.main_container);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            ratingText = (TextView) itemView.findViewById(R.id.item_rating);
            cookingTime= (TextView) itemView.findViewById(R.id.cooking_time);

        }

    }

    public void changeList(List<MenuData> data){
        this.data = data;
        this.notifyDataSetChanged();
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
