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
    private String itemRating;
    private String avgTime;

    public MenuData(int id,String imageUrl, String itemName, String itemType, String itemPrice,String itemRating,String avgTime) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemRating = itemRating;
        this.avgTime = avgTime;
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

    public String getItemRating() {
        return this.itemRating;
    }

    public String getAvgTime() {
        return this.avgTime;
    }
}
