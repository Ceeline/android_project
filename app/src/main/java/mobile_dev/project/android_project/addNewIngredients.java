package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class addNewIngredients extends AppCompatActivity {
    public static final String EXTRA_Name = "Name";
    public static final String EXTRA_Quantity = "Quantity";

    private EditText EditIngredientView;
    private SeekBar quantitySeekBar;
    private TextView quantityText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);
        EditIngredientView = findViewById(R.id.edit_word);
        quantitySeekBar = findViewById(R.id.simpleSeekBar);
        quantityText = findViewById(R.id.textQuantity);

        //quantityText.setText("i changed it");

        quantitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                quantityText.setText("" + progressValue);
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
            replyIntent.putExtra(EXTRA_Name, ingredient);
            replyIntent.putExtra(EXTRA_Quantity, seekBarValue);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
