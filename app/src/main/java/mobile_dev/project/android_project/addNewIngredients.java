package mobile_dev.project.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class addNewIngredients extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditIngredientView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);
        mEditIngredientView = findViewById(R.id.edit_word);

    }

    public void onClickSave (View view){
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditIngredientView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String ingredient = mEditIngredientView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, ingredient);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
