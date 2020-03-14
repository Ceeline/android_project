package mobile_dev.project.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class search_Activity extends AppCompatActivity {
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
    }

    public void onClickSearchBtn(View v)
    {
        //we get which radio button has been checked:
        RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        Intent replyIntent = new Intent();

        if (rb != null){

            //we get what the user entered
            TextView input = findViewById(R.id.inputSearch);
            String search = input.getText().toString();

            String url;

            if (rb.getId() == R.id.ingredientBtn){
                url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + search;
            }else{
                url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + search;
            }

            replyIntent.putExtra("URL", url);
            setResult(RESULT_OK, replyIntent);
            finish();

        }else {
            Toast.makeText(this, "Choose between ingredient and cocktail", Toast.LENGTH_SHORT).show();
        }

    }
}
