package mobile_dev.project.android_project.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

@Entity(tableName = "ingredients_table")
public class Ingredients {

    @PrimaryKey(autoGenerate = true)
    public int idIngredient;

    @NonNull
    @ColumnInfo(name = "ingredient")
    public String ingredient;

    /*@ColumnInfo(name = "inventoryquantity")
    public int inventoryQuantity;

    @ColumnInfo(name = "shoppingquantity")
    public int shoppingQuantity;

    @ColumnInfo(name = "inventory")
    public boolean inventory;

    @ColumnInfo(name = "shopping")
    public boolean shopping;*/


    public Ingredients(String ingredient) {
        this.ingredient = ingredient;
    }

    /*public int getIdIngredient() {
        return idIngredient;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public int getShoppingQuantity() {
        return shoppingQuantity;
    }

    public boolean isInventory() {
        return inventory;
    }

    public boolean isShopping() {
        return shopping;
    }

    public Ingredients(String ingredient, int quantity, int mode) {
        this.ingredient = ingredient;

        if (mode == 0) {
            this.inventoryQuantity = quantity;
            this.shoppingQuantity = 0;
            this.inventory = true;
            this.shopping = false;
        } else if (mode == 1) {
            this.inventoryQuantity = 0;
            this.shoppingQuantity = quantity;
            this.inventory = false;
            this.shopping = true;
        }
    }*/

    public String getIngredient() {
        return this.ingredient;
    }

    /*public JSONObject getIngredient2JSON() {
        JSONObject json = new JSONObject();


        return json;
    }*/


}
