package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

public class AppRepository {
    private IngredientsDao mIngredientsDao;
    private CocktailsDao mCocktailsDao;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientsDao = db.IngredientsDao();
        mCocktailsDao = db.CocktailsDao();
    }

    LiveData<List<Cocktail>> getAllFavorite() { return mCocktailsDao.getAll(); }

    LiveData<List<Ingredients>> getAllInventory() { return mIngredientsDao.getAllInventory(); }

    LiveData<List<Ingredients>> getAllShopping() {
        return mIngredientsDao.getAllShopping();
    }

    public int checkifExist(String name) {
        return mIngredientsDao.checkifExist(name);
    }

    public void insert(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.insert(ingredient));
    }

    public void insert(Cocktail cocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> mCocktailsDao.insert(cocktail));
    }

    void delete(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.delete(ingredient));
    }

    void update(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.update(ingredient));
    }

    void updateQuantity(int id, int quantity){
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.updateQuantity(id, quantity));
    }
}
