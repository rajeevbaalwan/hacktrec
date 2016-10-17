package in.curience.hacktrec.Models;

import java.io.Serializable;

/**
 * Created by RAJEEV YADAV on 10/17/2016.
 */
public class OrderedData implements Serializable {

    String itemName;
    String itemQuantity;
    String itemPrice;
    public OrderedData(String itemName,String itemQuantity,String itemPrice)
    {
        this.itemName=itemName;
        this.itemQuantity=itemQuantity;
        this.itemPrice=itemPrice;
    }
    public String getItemName()
    {
        return this.itemName;
    }
    public String getItemPrice()
    {
        return this.itemQuantity
    }

}
