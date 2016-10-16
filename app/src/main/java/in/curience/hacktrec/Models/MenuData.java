package in.curience.hacktrec.Models;

import java.io.Serializable;

/**
 * Created by RAJEEV YADAV on 10/16/2016.
 */
public class MenuData implements Serializable{
    private int id;
    private String imageUrl;
    private String itemName;
    private String itemType;
    private String itemPrice;

    public MenuData(int id,String imageUrl, String itemName, String itemType, String itemPrice) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
    }

    public int getId() {
        return this.id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemType() {
        return this.itemType;
    }

    public String getItemPrice() {
        return this.itemPrice;
    }
}
