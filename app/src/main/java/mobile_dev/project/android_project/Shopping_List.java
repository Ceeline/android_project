package mobile_dev.project.android_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import mobile_dev.project.android_project.database.Constants;
import mobile_dev.project.android_project.database.Ingredients;
import mobile_dev.project.android_project.database.IngredientsListAdapter;
import mobile_dev.project.android_project.database.IngredientsViewModel;

import static android.app.Activity.RESULT_OK;

public class Shopping_List extends Fragment implements IngredientsListAdapter.OnDeleteClickListener {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private Context globalContext = null;
    private IngredientsViewModel mIngredientViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalContext = this.getActivity();
        View root = inflater.inflate(R.layout.activity_main, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final IngredientsListAdapter adapter = new IngredientsListAdapter(this.getContext(), this, Constants.SHOPPING);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mIngredientViewModel = new ViewModelProvider(this).get(IngredientsViewModel.class);

        mIngredientViewModel.getAllShopping().observe(this, new Observer<List<Ingredients>>() {
            @Override
            public void onChanged(@Nullable final List<Ingredients> ingredient) {
                adapter.setIngredients(ingredient);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Celia", "clicked");
                Intent intent = new Intent(globalContext, addNewIngredients.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(addNewIngredients.EXTRA_Name);
            int qty = data.getIntExtra(addNewIngredients.EXTRA_Quantity, 0);

            if (name != null && !name.equals("")) {
                Ingredients ingredient = new Ingredients(name, qty, Constants.SHOPPING);
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
