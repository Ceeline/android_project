package mobile_dev.project.android_project.database;

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
        private final ImageButton ingredientBuy;
        private int mPosition;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
            ingredientQuantityView = itemView.findViewById(R.id.qtytext);
            ingredientDelete = itemView.findViewById(R.id.btnDelete);
            if (mode == SHOPPING) {
                ingredientBuy = itemView.findViewById(R.id.btnBought);
            } else {
                ingredientBuy = null;
            }
        }

        public void setData(String name, int quantity, int position) {
            ingredientItemView.setText(name);
            ingredientQuantityView.setText(String.valueOf(quantity));
            mPosition = position;
        }

        public void setListeners() {
            ingredientDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (OnDeleteClickListener != null) {
                        OnDeleteClickListener.fct_OnDeleteClickListener(mIngredients.get(mPosition));
                    }
                }
            });

            if (mode == SHOPPING) {
                ingredientDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (OnDeleteClickListener != null) {
                            OnDeleteClickListener.fct_OnDeleteClickListener(mIngredients.get(mPosition));
                        }
                    }
                });

                ingredientBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (OnUpdateClickListener != null) {
                            OnUpdateClickListener.fct_OnUpdateClickListener(mIngredients.get(mPosition));
                        }
                    }
                });
            }
        }
    }

    public interface OnDeleteClickListener {
        void fct_OnDeleteClickListener(Ingredients mIngredient);
    }
    public interface OnUpdateClickListener {
        void fct_OnUpdateClickListener(Ingredients mIngredient);
    }


    private final LayoutInflater mInflater;
    private List<Ingredients> mIngredients;
    private OnDeleteClickListener OnDeleteClickListener;
    private OnUpdateClickListener OnUpdateClickListener;
    private int mode;

    public IngredientsListAdapter(Context context, OnDeleteClickListener delL, OnUpdateClickListener upL, int mode) {
        mInflater = LayoutInflater.from(context);
        this.OnDeleteClickListener = delL;
        this.OnUpdateClickListener = upL;
        this.mode = mode;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (mode == Constants.INVENTORY) {
            itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        } else {
            itemView = mInflater.inflate(R.layout.recyclerview_item_shopping, parent, false);
        }

        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            Ingredients current = mIngredients.get(position);
            if (current.isInventoryList()) {
                holder.setData(current.getNameIngredient(), current.getInventoryQuantity(), position);
            } else {
                holder.setData(current.getNameIngredient(), current.getShoppingQuantity(), position);
            }
            holder.setListeners();
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
