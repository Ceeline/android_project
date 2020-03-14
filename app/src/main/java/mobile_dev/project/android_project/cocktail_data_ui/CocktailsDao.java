package mobile_dev.project.android_project.cocktail_data_ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

@Dao
public interface CocktailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cocktail cocktails);


    @Query("SELECT * FROM cocktails_table")
    LiveData<List<Cocktail>> getAll();

    @Query("SELECT count(*) FROM cocktails_table WHERE idApi = :id")
    int checkifExist(String id);

    @Delete
    void delete(Cocktail cocktail);

    @Query("DELETE FROM cocktails_table")
    void deleteAll();
}
