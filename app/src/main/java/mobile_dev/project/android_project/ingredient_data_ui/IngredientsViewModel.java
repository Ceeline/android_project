package mobile_dev.project.android_project.ingredient_data_ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mobile_dev.project.android_project.database.AppRepository;

public class IngredientsViewModel extends AndroidViewModel {

        private final AppRepository mRepository;

        public IngredientsViewModel (Application application) {
            super(application);
            mRepository = new AppRepository(application);
        }

        public LiveData<List<Ingredients>> getAllInventory() { return mRepository.getAllInventory(); }
        public LiveData<List<Ingredients>> getAllShopping() { return mRepository.getAllShopping(); }

        public void insert(Ingredients ingredient) { mRepository.insert(ingredient); }
        public void delete(Ingredients ingredient) { mRepository.delete(ingredient); }
        public void update(Ingredients ingredient) { mRepository.update(ingredient); }
        public void updateQuantity(int id, int quantity) { mRepository.updateInventoryQuantity(id, quantity); }
}
