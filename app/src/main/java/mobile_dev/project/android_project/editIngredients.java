package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mobile_dev.project.android_project.database.Constants;

public class editIngredients extends AppCompatActivity {

    private TextView EditIngredientView;
    private SeekBar quantitySeekBar;
    private TextView quantityText;
    private Intent receivedIntent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ingredient);
        EditIngredientView = findViewById(R.id.edit_word);
        receivedIntent = this.getIntent();
        EditIngredientView.setText(receivedIntent.getStringExtra(Constants.EXTRA_Name));

        quantitySeekBar = findViewById(R.id.simpleSeekBar);


        int qtyIntent = receivedIntent.getIntExtra(Constants.EXTRA_Quantity, 0);
        quantityText = findViewById(R.id.textQuantity);
        quantityText.setText(String.valueOf(qtyIntent));


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
            String name = EditIngredientView.getText().toString();
            replyIntent.putExtra(Constants.EXTRA_Name, name);
            replyIntent.putExtra(Constants.EXTRA_Quantity, seekBarValue);
            replyIntent.putExtra(Constants.EXTRA_Id, receivedIntent.getIntExtra(Constants.EXTRA_Id, 0));
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
