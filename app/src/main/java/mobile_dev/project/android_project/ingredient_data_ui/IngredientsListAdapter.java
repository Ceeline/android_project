package mobile_dev.project.android_project.ingredient_data_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mobile_dev.project.android_project.R;

import static mobile_dev.project.android_project.database.Constants.SHOPPING;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder> {


    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientItemView;
        private final TextView ingredientQuantityView;
        private final ImageButton ingredientDelete;
        private final ImageButton btnEdit;
        private int mPosition;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
            ingredientQuantityView = itemView.findViewById(R.id.qtytext);
            ingredientDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }

        void setData(Ingredients ingredient, int position) {
            String displayText = ingredient.getNameIngredient();
            int quantity;
            if (ingredient.isInventoryList()) {
                quantity = ingredient.getInventoryQuantity();
            } else {
                quantity = ingredient.getShoppingQuantity();
            }
            String qtyText = quantity + " " + ingredient.getUnite();

            ingredientItemView.setText(displayText);
            ingredientQuantityView.setText(qtyText);
            mPosition = position;
        }

        void setListeners() {

            ingredientDelete.setOnClickListener(view -> {
                if (OnDeleteClickListener != null) {
                    OnDeleteClickListener.fct_OnDeleteClickListener(mIngredients.get(mPosition));
                }
            });

            btnEdit.setOnClickListener(view -> {
                if (OnDeleteClickListener != null) {
                    OnDeleteClickListener.fct_OnUpdateClickListener(mIngredients.get(mPosition));
                }
            });
        }
    }

    public interface OnDeleteClickListener {
        void fct_OnDeleteClickListener(Ingredients mIngredient);

        void fct_OnUpdateClickListener(Ingredients mIngredient);
    }


    private final LayoutInflater mInflater;
    private List<Ingredients> mIngredients;
    private final OnDeleteClickListener OnDeleteClickListener;
    private final int mode;

    public IngredientsListAdapter(Context context, OnDeleteClickListener delL, int mode) {
        mInflater = LayoutInflater.from(context);
        this.OnDeleteClickListener = delL;
        this.mode = mode;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_ingredient, parent, false);

        if (mode == SHOPPING) {
            ImageButton edit = itemView.findViewById(R.id.btnEdit);
            edit.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }

        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            Ingredients current = mIngredients.get(position);
            holder.setData(current, position);
            holder.setListeners();
        } else {
            holder.ingredientItemView.setText(R.string.no_ingredient);
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
