package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.flashcard.MainActivity.KEY_CARD_OPTIONS;

public class AddCardActivity extends AppCompatActivity {

    ImageView ivCancel, ivSave;
    EditText etQuestion,etAnswer;
    EditText[] etOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ivCancel = findViewById(R.id.ivCancel);
        ivSave = findViewById(R.id.ivSave);
        etQuestion = findViewById(R.id.etQuestion);
        etAnswer = findViewById(R.id.etAnswer);
        etOptions = new EditText[]{findViewById(R.id.etOption1), findViewById(R.id.etOption2), findViewById(R.id.etOption3)};

        etQuestion.setText(getIntent().getStringExtra(MainActivity.KEY_CARD_QUESTION));
        etAnswer.setText(getIntent().getStringExtra(MainActivity.KEY_CARD_ANSWER));
        for(int j = 0; j < 3; j++) {
            etOptions[j].setText(getIntent().getStringExtra(MainActivity.KEY_CARD_OPTIONS[j]));
        }

        ivCancel.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        ivSave.setOnClickListener(view -> {
            if (etQuestion.getText().toString().isEmpty() || etAnswer.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter your question and answer.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_CARD_QUESTION, etQuestion.getText().toString());
                intent.putExtra(MainActivity.KEY_CARD_ANSWER, etAnswer.getText().toString());
                for(int j = 0; j < etOptions.length; j++) {
                    intent.putExtra(MainActivity.KEY_CARD_OPTIONS[j], etOptions[j].getText().toString());
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}