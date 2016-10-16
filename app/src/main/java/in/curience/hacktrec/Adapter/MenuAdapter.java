package in.curience.hacktrec.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.curience.hacktrec.Models.MenuData;
import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {

    private LayoutInflater inflater;

    private List<MenuData> data;

    public  MenuAdapter(List<MenuData> data) {
        this.data=data;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.itemName.setText(data.get(position).getItemName());
        holder.itemPrice.setText(data.get(position).getItemPrice());
        holder.itemType.setText(data.get(position).getItemType());


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
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName= (TextView) itemView.findViewById(R.id.item_name);
            itemPic= (ImageView) itemView.findViewById(R.id.item_pic);
            itemType= (TextView) itemView.findViewById(R.id.item_type);
            itemPrice= (TextView) itemView.findViewById(R.id.item_price);
        }

    }

    public void changeList(List<MenuData> data){
        this.data = data;
        this.notifyDataSetChanged();
    }
}
