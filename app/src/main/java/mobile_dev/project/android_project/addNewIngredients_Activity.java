package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mobile_dev.project.android_project.database.Constants;

public class addNewIngredients_Activity extends AppCompatActivity {

    private EditText EditIngredientView;
    private SeekBar quantitySeekBar;
    private TextView quantityText;
    private Spinner spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        EditIngredientView = findViewById(R.id.edit_word);
        quantitySeekBar = findViewById(R.id.simpleSeekBar);
        quantityText = findViewById(R.id.textQuantity);
        spinner = findViewById(R.id.unitChoice);

        quantitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                quantityText.setText(String.valueOf(progressValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void onClickSave(View view) {
        int seekBarValue = quantitySeekBar.getProgress();

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(EditIngredientView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String ingredient = EditIngredientView.getText().toString();
            replyIntent.putExtra(Constants.EXTRA_Name, ingredient);
            replyIntent.putExtra(Constants.EXTRA_Quantity, seekBarValue);
            replyIntent.putExtra(Constants.EXTRA_Unit, String.valueOf(spinner.getSelectedItem()));
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
