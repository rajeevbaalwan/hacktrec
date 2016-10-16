package in.curience.hacktrec.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import in.curience.hacktrec.Activities.MenuData;
import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ItemViewHolder> {
    private LayoutInflater inflater;
   List<MenuData> data= Collections.emptyList();
    public  MenuAdapter(Context context,List<MenuData> data)
    {
        inflater=LayoutInflater.from(context);
        this.data=data;

    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view= inflater.inflate(R.layout.menu_single_item_layout,parent,false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
      MenuData current=data.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView itemPic;
        TextView itemName;
        TextView itemType;
        TextView itemPrice;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName= (TextView) itemView.findViewById(R.id.item_name);
           itemPic= (ImageView) itemView.findViewById(R.id.item_pic);
            itemType= (TextView) itemView.findViewById(R.id.item_type);
            itemPrice= (TextView) itemView.findViewById(R.id.item_price);
        }

    }
}
