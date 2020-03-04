package mobile_dev.project.android_project.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "ingredients_table")
public class Ingredients {

    @PrimaryKey(autoGenerate = true)
    public int idIngredient;

    @NonNull
    @ColumnInfo(name = "nameingredient")
    public String nameIngredient;

    @NonNull
    @ColumnInfo(name = "inventoryquantity")
    public int inventoryQuantity;

    @ColumnInfo(name = "shoppingquantity")
    public int shoppingQuantity;

    @ColumnInfo(name = "inventoryList")
    public boolean inventoryList;

    @ColumnInfo(name = "shoppingList")
    public boolean shoppingList;


    public Ingredients(@NonNull String nameIngredient) {
        this.nameIngredient = nameIngredient;
        this.inventoryQuantity = 0;
        this.shoppingQuantity = 0;
        this.inventoryList = true;
    }


    public Ingredients(@NonNull String nameIngredient, int inventoryQuantity, int mode) {
        this.nameIngredient = nameIngredient;

        if (mode == Constants.INVENTORY) {
            this.inventoryQuantity = inventoryQuantity;
            this.shoppingQuantity = 0;
            this.inventoryList = true;
            this.shoppingList = false;
        } else if (mode == Constants.SHOPPING) {
            this.inventoryQuantity = 0;
            this.shoppingQuantity = inventoryQuantity;
            this.inventoryList = false;
            this.shoppingList = true;
        }
    }

    @NonNull
    public String getNameIngredient() {
        return nameIngredient;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public int getShoppingQuantity() {
        return shoppingQuantity;
    }

    public boolean isInventoryList() {
        return inventoryList;
    }



}
