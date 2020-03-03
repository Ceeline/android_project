package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private IngredientsDao mIngredientsDao;
    private LiveData<List<Ingredients>> mAllIngredients;

    AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientsDao = db.IngredientsDao();
        mAllIngredients = mIngredientsDao.getAll();
    }

    LiveData<List<Ingredients>> getAllIngredients() {
        return mAllIngredients;
    }

    void insert(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> mIngredientsDao.insert(ingredient));
    }
}
