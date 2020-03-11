package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mobile_dev.project.android_project.frag_Cocktail.DownloaderTask;
import mobile_dev.project.android_project.search_cocktail.DropdownListAdapter;
import mobile_dev.project.android_project.ui.SectionsPagerAdapter;

public class Basic_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    DropdownListAdapter adapter;
    SearchView search;
    ArrayList<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);


        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Log.i("HELLO", "Hellooo");
                // The search button was pressed

                //we initialize the list of the dropdown
                list = findViewById(R.id.listview);
                arraylist = new ArrayList<>();
                adapter = new DropdownListAdapter(this, arraylist);
                list.setAdapter(adapter);

                // Create an onItemClick function for the list view
                AdapterView.OnItemClickListener messageClickedHandler = (AdapterView<?> parent, View v, int position, long id) -> {
                    onQueryTextSubmit((String)parent.getItemAtPosition(position));
                };

                // Attach the event to the listView
                list.setOnItemClickListener(messageClickedHandler);

                search = findViewById(R.id.search);
                search.setOnQueryTextListener(this);

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                Intent intent = new Intent(this, Favoris.class);
                startActivity(intent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                // throw error
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SUBMIT", query);

        String url = null;
        String[] input = query.split(" ");

        if (query.equals(arraylist.get(0))){
            url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + input[1];
        }else if (query.equals(arraylist.get(1))){
            url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" +  input[1];
        }else {
            //send a toast to say that the user has to choose one of the two proposition of the dropdown
        }

        if (url != null){
            ListView listView = findViewById(R.id.listCocktails);
            DownloaderTask downloader = new DownloaderTask(this, listView, url);
            downloader.execute();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

}