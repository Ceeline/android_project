package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//to retrieve data from the API
public class DownloaderTask extends AsyncTask <String, Integer, Cocktail>{
    private Context ctxt;
    private ListView listCocktails;
    CocktailsAdapter adapter;
    String url;

    public DownloaderTask(Context ctxt, ListView listCocktails, String url) {
        this.ctxt = ctxt;
        this.listCocktails = listCocktails;

        // Create the adapter to convert the array to views
        this.adapter = new CocktailsAdapter(ctxt);
        this.listCocktails.setAdapter(adapter);

        this.url = url;
    }

    @Override
    protected Cocktail doInBackground(String... strings) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctxt);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //we structure the json to remove {drinks:} from the data
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("drinks");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayList<Cocktail> newCocktails = null;

                        if (url.contains("search"))
                            newCocktails = Cocktail.fromJson(jsonArray,1);
                        else
                            newCocktails = Cocktail.fromJson(jsonArray,2);

                        adapter.addAll(newCocktails);

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR_LOG", String.valueOf(error));
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        return null;
    }

}

