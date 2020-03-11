package mobile_dev.project.android_project.search_cocktail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import mobile_dev.project.android_project.R;

public class DropdownListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> arrayList;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
    }

    public DropdownListAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // Get the data item for this position
        String choice = (String) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (view == null)
        {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_search_list, parent, false);
            viewHolder.name = view.findViewById(R.id.search_Name);

            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        }
        else
        {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Populate the data into the template view using the data object
        assert choice != null;
        viewHolder.name.setText(choice);

        // Return the completed view to render on screen
        return view;
    }

    // Filter Class
    public void filter(String inputText) {
        inputText = inputText.toLowerCase(Locale.getDefault());
        arrayList.clear();
        if (inputText.length() != 0) {
            arrayList.add("Ingredients: " + inputText);
            arrayList.add("Cocktail: " + inputText);
        }
        notifyDataSetChanged();
    }
}
