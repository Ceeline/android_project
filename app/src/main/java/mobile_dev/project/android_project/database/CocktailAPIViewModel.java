package mobile_dev.project.android_project.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

public class CocktailAPIViewModel extends AndroidViewModel {

        private MutableLiveData<List<Cocktail>> cocktailsList;
        private Context context;

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

            Log.i("celia", "im in load");
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("celia","onResponse");
                            List<Cocktail> test;
                            //we structure the json to remove {drinks:} from the data
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = response.getJSONArray("drinks");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            test = Cocktail.fromJson(jsonArray);
                            Log.i("celia", "value of array " + test);
                            cocktailsList.setValue(test);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ERROR_LOG", String.valueOf(error));
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        }
}
