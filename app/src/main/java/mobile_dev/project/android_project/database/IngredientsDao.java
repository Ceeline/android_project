package mobile_dev.project.android_project.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredients ingredient);

    @Query("SELECT * FROM ingredients_table WHERE inventoryList = 1")
    LiveData<List<Ingredients>> getAllInventory();

    @Query("SELECT * FROM ingredients_table WHERE shoppingList = 1")
    LiveData<List<Ingredients>> getAllShopping();

    @Query("UPDATE ingredients_table SET inventoryQuantity = :quantity WHERE idIngredient = :id")
    void updateQuantity(int id, int quantity);

    @Query("SELECT count(*) FROM ingredients_table WHERE nameingredient = :name")
    int checkifExist(String name);

    @Update
    void update(Ingredients ingredient);

    @Delete
    int delete(Ingredients ingredient);

    @Query("DELETE FROM ingredients_table")
    void deleteAll();
}
