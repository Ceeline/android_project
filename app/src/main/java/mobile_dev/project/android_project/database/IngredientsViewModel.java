package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientsViewModel extends AndroidViewModel {

        private AppRepository mRepository;

        private LiveData<List<Ingredients>> mAllIngredients;

        public IngredientsViewModel (Application application) {
            super(application);
            mRepository = new AppRepository(application);
            mAllIngredients = mRepository.getAllIngredients();
        }

        public LiveData<List<Ingredients>> getAllIngredients() { return mAllIngredients; }

        public void insert(Ingredients ingredient) { mRepository.insert(ingredient); }
}
