package mobile_dev.project.android_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


/* Code inspired by : https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */

public class CocktailsAdapter extends ArrayAdapter<Cocktail> {

    Context ctxt;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView image;
    }

    public CocktailsAdapter(Context context) {
        super(context, R.layout.item_cocktail_list);
        ctxt = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Cocktail cocktail = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null)
        {

            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_cocktail_list, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.cocktail_Name);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.cocktail_Img);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else
        {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.name.setText(cocktail.name);
        getImageFromURL(cocktail.image, viewHolder.image);

        // Return the completed view to render on screen
        return convertView;
    }


    private void getImageFromURL(String url, final ImageView image) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctxt);

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        image.setImageBitmap(response);
                        Log.i("IMAGE", response.toString());
                    }

                }, 0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR_LOG", error.toString());
                    }
                });


        // Add the request to the RequestQueue.
        queue.add(imageRequest);

    }
}
