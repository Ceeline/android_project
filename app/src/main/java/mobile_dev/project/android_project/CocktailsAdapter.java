package mobile_dev.project.android_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/* Code inspired by : https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView */

public class CocktailsAdapter extends ArrayAdapter<Cocktail> {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView image;
    }

    public CocktailsAdapter(Context context, ArrayList<Cocktail> cocktails) {
        super(context, R.layout.item_cocktail_list, cocktails);
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
        viewHolder.image.setImageBitmap(cocktail.image);

        // Return the completed view to render on screen
        return convertView;
    }
}
