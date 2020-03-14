package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import mobile_dev.project.android_project.cocktail_data_ui.CocktailsDao;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;
import mobile_dev.project.android_project.ingredient_data_ui.Ingredients;
import mobile_dev.project.android_project.ingredient_data_ui.IngredientsDao;

public class AppRepository {
    private final IngredientsDao mIngredientsDao;
    private final CocktailsDao mCocktailsDao;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientsDao = db.IngredientsDao();
        mCocktailsDao = db.CocktailsDao();
    }

    public LiveData<List<Cocktail>> getAllFavorite() { return mCocktailsDao.getAll(); }

    public LiveData<List<Ingredients>> getAllInventory() { return mIngredientsDao.getAllInventory(); }

    public LiveData<List<Ingredients>> getAllShopping() {
        return mIngredientsDao.getAllShopping();
    }

    public int checkifExist(String name) {
        return mIngredientsDao.checkifExist(name);
    }

    public int checkExistCockatil(String id) {
        return mCocktailsDao.checkifExist(id);
    }

    public void insert(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.insert(ingredient));
    }

    public void insert(Cocktail cocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> mCocktailsDao.insert(cocktail));
    }

    public void delete(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.delete(ingredient));
    }

    public void delete(Cocktail cocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> mCocktailsDao.delete(cocktail));
    }

    public void update(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.update(ingredient));
    }

    public void updateInventoryQuantity(int id, int quantity){
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.updateInventoryQuantity(id, quantity));
    }
}
