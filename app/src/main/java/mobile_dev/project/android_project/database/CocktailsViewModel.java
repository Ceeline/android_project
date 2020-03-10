package mobile_dev.project.android_project.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

public class CocktailsViewModel extends AndroidViewModel {

        private AppRepository mRepository;

        public CocktailsViewModel(Application application) {
            super(application);
            mRepository = new AppRepository(application);
        }

        public LiveData<List<Cocktail>> getAll() { return mRepository.getAllFavorite(); }

        public void insert(Ingredients ingredient) { mRepository.insert(ingredient); }
        public void delete(Ingredients ingredient) { mRepository.delete(ingredient); }
}
