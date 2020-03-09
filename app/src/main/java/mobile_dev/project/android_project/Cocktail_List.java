package mobile_dev.project.android_project;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;
import mobile_dev.project.android_project.frag_Cocktail.CocktailDetail;
import mobile_dev.project.android_project.frag_Cocktail.DownloaderTask;

public class Cocktail_List extends Fragment {
    char filter;
    private Context context;
    private View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getContext();
        View root = inflater.inflate(R.layout.activity_cocktail__list, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mView = view;

        // Creation of a ListView
        ListView listView = view.findViewById(R.id.listCocktails);

        // add listener on spinner filters
        addListenerOnSpinnerItemSelection();

        // Create an onItemClick function for the list view
        AdapterView.OnItemClickListener messageClickedHandler = (AdapterView<?> parent, View v, int position, long id) -> {
            Cocktail cocktail = (Cocktail) parent.getItemAtPosition(position);
            openActivity(cocktail);
        };

        // Attach the event to the listView
        listView.setOnItemClickListener(messageClickedHandler);
    }

    public void openActivity(Cocktail cocktail) {
        Intent intent = new Intent(context, CocktailDetail.class);
        intent.putExtra("idCocktail", cocktail.getId());
        startActivity(intent);
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
                ArrayList<String> list = new ArrayList<String>();

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

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
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
                ListView listView = mView.findViewById(R.id.listCocktails);

                //we get the url for the request to the API
                String url;
                if (filter == 'l'){
                    url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=" + choice;
                }
                else{
                    url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?" + filter + "=" + choice;
                }

                DownloaderTask downloader = new DownloaderTask(context, listView, url);
                downloader.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }

        spinnerChoice.setOnItemSelectedListener(new CustomOnChoiceSelectedListener());
    }

}
