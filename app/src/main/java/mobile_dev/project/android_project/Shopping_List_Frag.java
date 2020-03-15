package mobile_dev.project.android_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.ingredient_data_ui.Ingredients;
import mobile_dev.project.android_project.ingredient_data_ui.IngredientsListAdapter;
import mobile_dev.project.android_project.ingredient_data_ui.IngredientsViewModel;

import static android.app.Activity.RESULT_OK;

public class Shopping_List_Frag extends Fragment implements IngredientsListAdapter.OnDeleteClickListener {

    private Context globalContext = null;
    private IngredientsViewModel mIngredientViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalContext = this.getActivity();
        return inflater.inflate(R.layout.frag_inventory_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final IngredientsListAdapter adapter = new IngredientsListAdapter(this.getContext(), this, Constants.SHOPPING);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mIngredientViewModel = new ViewModelProvider(this).get(IngredientsViewModel.class);

        mIngredientViewModel.getAllShopping().observe(this, ingredient -> adapter.setIngredients(ingredient));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(globalContext, addNewIngredients_Activity.class);
            startActivityForResult(intent, Constants.NEW_ING_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.NEW_ING_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(Constants.EXTRA_Name);
            int qty = data.getIntExtra(Constants.EXTRA_Quantity, 0);

            if (name != null && !name.equals("")) {
                Ingredients ingredient = new Ingredients(name, qty, Constants.SHOPPING, null);
                mIngredientViewModel.insert(ingredient);
            } else {
                Toast.makeText(
                        globalContext,
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(
                    globalContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void fct_OnDeleteClickListener(Ingredients mIngredient) {
        if (!mIngredient.inventoryList) {
            mIngredientViewModel.delete(mIngredient);
        } else {
            mIngredient.shoppingList = false;
            mIngredient.shoppingQuantity = 0;
            mIngredientViewModel.update(mIngredient);
        }
    }

    @Override
    public void fct_OnUpdateClickListener(Ingredients mIngredient) {
        mIngredient.shoppingList = false;
        mIngredient.inventoryList = true;
        mIngredient.inventoryQuantity = mIngredient.inventoryQuantity + mIngredient.shoppingQuantity;
        mIngredient.shoppingQuantity = 0;
        mIngredientViewModel.update(mIngredient);
    }
}
