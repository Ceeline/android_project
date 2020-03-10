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
import mobile_dev.project.android_project.database.Ingredients;
import mobile_dev.project.android_project.database.IngredientsListAdapter;
import mobile_dev.project.android_project.database.IngredientsViewModel;

import static android.app.Activity.RESULT_OK;


public class Inventory_List extends Fragment implements IngredientsListAdapter.OnDeleteClickListener {

    private IngredientsViewModel mIngredientViewModel;
    private Context globalContext = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalContext = this.getActivity();
        View root = inflater.inflate(R.layout.activity_inventory_list, null);
        return root;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final IngredientsListAdapter adapter = new IngredientsListAdapter(this.getContext(), this, Constants.INVENTORY);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mIngredientViewModel = new ViewModelProvider(this).get(IngredientsViewModel.class);

        mIngredientViewModel.getAllInventory().observe(this, adapter::setIngredients);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(globalContext, addNewIngredients.class);
            startActivityForResult(intent, Constants.NEW_ING_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.NEW_ING_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(Constants.EXTRA_Name);
            int qty = data.getIntExtra(Constants.EXTRA_Quantity, 0);

            if (name != null && !name.equals("")) {
                // TODO ? put dropdown for unit ?
                Ingredients ingredient = new Ingredients(name, qty, Constants.INVENTORY, null);
                mIngredientViewModel.insert(ingredient);
            } else {
                Toast.makeText(
                        globalContext.getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == Constants.EDIT_ING_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {


            int qty = data.getIntExtra(Constants.EXTRA_Quantity, 0);
            int id = data.getIntExtra(Constants.EXTRA_Id, 0);

            mIngredientViewModel.updateQuantity(id, qty);
        } else {
            Toast.makeText(
                    globalContext.getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void fct_OnDeleteClickListener(Ingredients mIngredient) {
        if (!mIngredient.shoppingList) {
            mIngredientViewModel.delete(mIngredient);
        } else {
            mIngredient.inventoryList = false;
            mIngredient.inventoryQuantity = 0;
            mIngredientViewModel.update(mIngredient);
        }
    }

    @Override
    public void fct_OnUpdateClickListener(Ingredients mIngredient) {
        Intent intent = new Intent(globalContext, editIngredients.class);
        intent.putExtra(Constants.EXTRA_Id, mIngredient.idIngredient);
        intent.putExtra(Constants.EXTRA_Name, mIngredient.nameIngredient);
        intent.putExtra(Constants.EXTRA_Quantity, mIngredient.inventoryQuantity);
        startActivityForResult(intent, Constants.EDIT_ING_ACTIVITY_REQUEST_CODE);
    }
}

