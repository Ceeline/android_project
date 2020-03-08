package mobile_dev.project.android_project.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

import java.util.List;

@Dao
public interface CocktailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cocktail cocktails);


    @Query("SELECT * FROM cocktails_table")
    LiveData<List<Cocktail>> getAll();

    @Update
    void update(Cocktail cocktail);

    @Delete
    int delete(Cocktail cocktail);

    @Query("DELETE FROM cocktails_table")
    void deleteAll();
}
