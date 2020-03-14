package mobile_dev.project.android_project.cocktail_data_ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

public class CocktailAPIViewModel extends AndroidViewModel {

        private MutableLiveData<List<Cocktail>> cocktailsList;
        private final Context context;

        public CocktailAPIViewModel(Application application) {
            super(application);
            context = application.getApplicationContext();
        }

        public LiveData<List<Cocktail>> getAll() {
            if (cocktailsList == null) {
                cocktailsList = new MutableLiveData<>();
            }
            return cocktailsList;
        }

        public void loadCocktails(String url) {

            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, response -> {
                        List<Cocktail> cocktails;
                        //we structure the json to remove {drinks:} from the data
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("drinks");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (jsonArray != null) {
                            cocktails = Cocktail.fromJson(jsonArray);
                            cocktailsList.setValue(cocktails);
                        }

                    }, error -> {
                        Log.i("ERROR_LOG", String.valueOf(error));
                        Toast.makeText(context, "No result found", Toast.LENGTH_LONG).show();
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }
}
