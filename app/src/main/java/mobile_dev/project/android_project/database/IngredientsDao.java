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


    @Query("SELECT * FROM ingredients_table")
    LiveData<List<Ingredients>> getAll();

    @Query("SELECT * FROM ingredients_table WHERE inventoryList = 1")
    LiveData<List<Ingredients>> getAllInventory();

    @Query("SELECT * FROM ingredients_table WHERE shoppingList = 1")
    LiveData<List<Ingredients>> getAllShopping();

    /* @Query("SELECT * FROM ingredients WHERE iid IN (:ingredientsIds)")
     List<Ingredients> loadAllByIds(int[] userIds);

     @Query("SELECT * FROM ingredients WHERE first_name LIKE :first AND " +
             "last_name LIKE :last LIMIT 1")
     Ingredients findByName(String first, String last);
*/
    /*@Insert
    void insertAll(Ingredients... ingredients);
*/

    @Update
    void update(Ingredients ingredient);

    @Delete
    int delete(Ingredients ingredient);

    @Query("DELETE FROM ingredients_table")
    void deleteAll();
}
