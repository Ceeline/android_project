package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

//to retrieve data from the API
class DownloaderTask extends AsyncTask<String, Integer, Cocktail> {
    private final Context ctxt;
    private final String url;
    private Cocktail cocktail;
    private final OnPostInterface inter;

    DownloaderTask(Context ctxt, String url, OnPostInterface inter) {
        this.ctxt = ctxt;
        this.url = url;
        this.inter = inter;
    }

    @Override
    protected Cocktail doInBackground(String... strings) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctxt);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {


                    JSONArray jsonArray;
                    try {
                        //we structure the json to remove {drinks:} from the data
                        jsonArray = response.getJSONArray("drinks");

                        //download detail for one cocktail
                        cocktail = Cocktail.fromJson(jsonArray.getJSONObject(0));
                        inter.onPostExecute(cocktail);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.i("ERROR_LOG", String.valueOf(error)));

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        return null;
    }

}

