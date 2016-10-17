package in.curience.hacktrec.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.curience.hacktrec.Activities.SingleItem;
import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {

    private List<MenuData> data;
    private Context context;

    public  MenuAdapter(Context context,List<MenuData> data) {
        this.data=data;
        this.context=context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        holder.itemName.setText(data.get(position).getItemName());
        holder.itemPrice.setText(data.get(position).getItemPrice());
        holder.itemType.setText(data.get(position).getItemType());
        final int pos = position;
        holder.clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), SingleItem.class);
                intent.putExtra("details",data.get(pos));
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
        private TextView itemPrice;
        private LinearLayout clickableLayout;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName= (TextView) itemView.findViewById(R.id.item_name);
            itemPic= (ImageView) itemView.findViewById(R.id.item_pic);
            itemType= (TextView) itemView.findViewById(R.id.item_type);
            itemPrice= (TextView) itemView.findViewById(R.id.item_price);
            clickableLayout = (LinearLayout) itemView.findViewById(R.id.main_container);
        }

    }

    public void changeList(List<MenuData> data){
        this.data = data;
        this.notifyDataSetChanged();
    }
}
