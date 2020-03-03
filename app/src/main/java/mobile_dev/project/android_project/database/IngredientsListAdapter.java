package mobile_dev.project.android_project.database;

import android.content.Context;
import android.util.Log;
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
        private final TextView ingredientQuantityView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
            ingredientQuantityView = itemView.findViewById(R.id.qtytext);
        }
    }

    private final LayoutInflater mInflater;
    private List<Ingredients> mIngredients;

    public IngredientsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            Ingredients current = mIngredients.get(position);
            holder.ingredientItemView.setText(current.getNameIngredient());
            if (current.isInventoryList()) {
                holder.ingredientQuantityView.setText(String.valueOf(current.getInventoryQuantity()));
            } else {
                holder.ingredientQuantityView.setText(String.valueOf(current.getShoppingQuantity()));
            }
        } else {
            holder.ingredientItemView.setText("No Ingredients");
        }
    }

    public void setIngredients(List<Ingredients> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }
}
