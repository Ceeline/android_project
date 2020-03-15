package mobile_dev.project.android_project.frag_Cocktail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

//to retrieve data from the API
public class BitmapDownloaderTask extends AsyncTask <String, Integer, Bitmap>{
    private final Context ctxt;
    private final ImageView image;
    private final String url;

    public BitmapDownloaderTask(Context ctxt, String url, final ImageView image) {
        this.ctxt = ctxt;
        this.image = image;
        this.url = url;
    }

    @Override
    public Bitmap doInBackground(String... strings) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctxt);

        ImageRequest imageRequest = new ImageRequest(url,
                response -> {
                    image.setImageBitmap(response);
                }, 0, 0, ImageView.ScaleType.CENTER, null,
                error -> Log.i("ERROR_LOG", error.toString()));


        // Add the request to the RequestQueue.
        queue.add(imageRequest);

        return null;

    }

}

