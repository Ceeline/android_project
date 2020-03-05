package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;

import java.util.ArrayList;

public class Cocktail_List extends AppCompatActivity {
    char filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail__list);


        // Creation of a ListView
        ListView listView = findViewById(R.id.listCocktails);

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

    public void openActivity (Cocktail cocktail) {
        Intent intent = new Intent(this, CocktailDetail.class);
        intent.putExtra("cocktail", cocktail);

        startActivity(intent);
    }

    private void addListenerOnSpinnerItemSelection(){
        //Spinner letter
        Spinner spinnerFilter = (Spinner) findViewById(R.id.spinner);

        class CustomOnFilterSelectedListener implements AdapterView.OnItemSelectedListener{

            Context ctxt;

            CustomOnFilterSelectedListener(Context ctxt){
                this.ctxt =ctxt;
            }
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                filter = parent.getItemAtPosition(pos).toString().toLowerCase().charAt(0);
                String filterName = parent.getItemAtPosition(pos).toString();

                //we display the different choices for this category in the second spinner
                Spinner spinnerChoice = (Spinner) findViewById(R.id.spinnerChoice);
                ArrayList<String> list = new ArrayList<String>();

                switch (filterName){
                    case "Alcoholic":
                        list.add("Alcoholic");
                        list.add("Non_Alcoholic");
                        list.add("Optional_Alcohol");
                        break;
                    case "Ingredient":
                        //TODO
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
                        for (int i = 97; i <= 122 ; i++)
                        {
                            list.add(Character.toString( (char)i ));
                        }
                        break;
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ctxt,android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerChoice.setAdapter(dataAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }

        spinnerFilter.setOnItemSelectedListener(new CustomOnFilterSelectedListener(this));


        Spinner spinnerChoice = (Spinner) findViewById(R.id.spinnerChoice);

        class CustomOnChoiceSelectedListener implements AdapterView.OnItemSelectedListener{
            Context ctxt;

            CustomOnChoiceSelectedListener(Context ctxt){
                this.ctxt =ctxt;
            }

            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                String choice = parent.getItemAtPosition(pos).toString();
                ListView listView = findViewById(R.id.listCocktails);

                DownloaderTask downloader = new DownloaderTask(ctxt, listView);
                String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?" + filter + "=" + choice;
                Log.i("CCC", "url: "+url);
                downloader.execute(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }

        spinnerChoice.setOnItemSelectedListener(new CustomOnChoiceSelectedListener(this));
    }

}
