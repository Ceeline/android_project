package mobile_dev.project.android_project.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mobile_dev.project.android_project.R;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder> {

        class IngredientViewHolder extends RecyclerView.ViewHolder {
            private final TextView ingredientItemView;

            private IngredientViewHolder(View itemView) {
                super(itemView);
                ingredientItemView = itemView.findViewById(R.id.textView);
            }
        }

        private final LayoutInflater mInflater;
        private List<Ingredients> mIngredients; // Cached copy of words

        public IngredientsListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

        @Override
        public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new IngredientViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(IngredientViewHolder holder, int position) {
            if (mIngredients != null) {
                Ingredients current = mIngredients.get(position);
                holder.ingredientItemView.setText(current.getIngredient());
            } else {
                // Covers the case of data not being ready yet.
                holder.ingredientItemView.setText("No Ingredients");
            }
        }

        public void setIngredients(List<Ingredients> ingredients){
            mIngredients = ingredients;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mIngredients != null)
                return mIngredients.size();
            else return 0;
        }
}
