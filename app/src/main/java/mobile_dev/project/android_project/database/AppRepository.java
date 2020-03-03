package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private IngredientsDao mIngredientsDao;
    private LiveData<List<Ingredients>> mAllIngredients;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientsDao = db.IngredientsDao();
        mAllIngredients = mIngredientsDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Ingredients>> getAllIngredients() {
        return mAllIngredients;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Ingredients ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIngredientsDao.insert(ingredient);
        });
    }
}
