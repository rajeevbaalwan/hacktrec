package in.curience.hacktrec.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.curience.hacktrec.Models.OrderedData;
import in.curience.hacktrec.R;

/**
 * Created by RAJEEV YADAV on 10/17/2016.
 */
public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.ItemViewHolder> {
    private List<OrderedData> orderList;
    private Context context;
    public OrderedItemAdapter(Context context,List<OrderedData> orderList)
    {
      this.orderList=orderList;
        this.context=context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.my_order_single_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemName.setText(orderList.get(position).getItemName());
        holder.itemPrice.setText(orderList.get(position).getItemPrice());
        holder.itemQuantity.setText(orderList.get(position).getItemQuantity());
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }
    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemQuantity;
        TextView itemPrice;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName= (TextView) itemView.findViewById(R.id.ordered_item_name);
            itemQuantity= (TextView) itemView.findViewById(R.id.ordered_item_quantity);
            itemPrice= (TextView) itemView.findViewById(R.id.ordered_item_amount);
        }
    }
}
