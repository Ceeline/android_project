package mobile_dev.project.android_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import mobile_dev.project.android_project.cocktail_data_ui.CocktailAPIViewModel;
import mobile_dev.project.android_project.cocktail_data_ui.CocktailsListAdapter;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;
import mobile_dev.project.android_project.frag_Cocktail.CocktailDetail;

import static android.app.Activity.RESULT_OK;

public class Cocktail_List_Frag extends Fragment implements CocktailsListAdapter.OnCocktailListener {
    private char filter;
    private Context context;
    private View mView;
    private CocktailAPIViewModel mCocktailViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getContext();
        return inflater.inflate(R.layout.frag_cocktail_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mView = view;

        RecyclerView recyclerView = view.findViewById(R.id.listCocktails);
        final CocktailsListAdapter adapter = new CocktailsListAdapter(this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mCocktailViewModel = new ViewModelProvider(this).get(CocktailAPIViewModel.class);

        mCocktailViewModel.getAll().observe(this, adapter::setCocktails);


        // add listener on spinner filters
        addListenerOnSpinnerItemSelection();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent (context, search_Activity.class);
            startActivityForResult(intent, 1);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String url = data.getStringExtra("URL");
            getCocktailsFromApi(url);
        }
    }

    private void addListenerOnSpinnerItemSelection() {
        //Spinner letter
        Spinner spinnerFilter = mView.findViewById(R.id.spinner);
        Spinner spinnerChoice = mView.findViewById(R.id.spinnerChoice);

        class CustomOnFilterSelectedListener implements AdapterView.OnItemSelectedListener {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                filter = parent.getItemAtPosition(pos).toString().toLowerCase().charAt(0);
                String filterName = parent.getItemAtPosition(pos).toString();

                //we display the different choices for this category in the second spinner
                ArrayList<String> list = new ArrayList<>();

                switch (filterName) {
                    case "Alcoholic":
                        list.add("Alcoholic");
                        list.add("Non_Alcoholic");
                        list.add("Optional_Alcohol");
                        break;
                    case "Category":
                        list.add("Ordinary_Drink");
                        list.add("Cocktail");
                        list.add("Cocoa");
                        list.add("Shot");
                        list.add("Homemade Liqueur");
                        list.add("Beer");
                        break;
                    case "Letter":
                        for (int i = 97; i <= 122; i++) {
                            list.add(Character.toString((char) i));
                        }
                        break;
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerChoice.setAdapter(dataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }

        spinnerFilter.setOnItemSelectedListener(new CustomOnFilterSelectedListener());


        class CustomOnChoiceSelectedListener implements AdapterView.OnItemSelectedListener {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String choice = parent.getItemAtPosition(pos).toString();

                //we get the url for the request to the API
                String url;
                if (filter == 'l'){
                        url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=" + choice;
                }
                else{
                    url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?" + filter + "=" + choice;
                }

                getCocktailsFromApi(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }

        spinnerChoice.setOnItemSelectedListener(new CustomOnChoiceSelectedListener());
    }

    private void getCocktailsFromApi(String url){
        mCocktailViewModel.loadCocktails(url);
    }

    @Override
    public void OnCocktailClick(Cocktail cocktail) {
        // Display info of the selected cocktail
        Intent intent = new Intent(this.getContext(), CocktailDetail.class);
        intent.putExtra("idCocktail", cocktail.getIdApi());
        startActivity(intent);
    }
}
