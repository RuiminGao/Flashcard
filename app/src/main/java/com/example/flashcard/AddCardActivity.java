package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    ImageView ivCancel, ivSave;
    EditText etQuestion,etAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ivCancel = findViewById(R.id.ivCancel);
        ivSave = findViewById(R.id.ivSave);
        etQuestion = findViewById(R.id.etQuestion);
        etAnswer = findViewById(R.id.etAnswer);

        etQuestion.setText(getIntent().getStringExtra(MainActivity.KEY_CARD_QUESTION));
        etAnswer.setText(getIntent().getStringExtra(MainActivity.KEY_CARD_ANSWER));

        ivCancel.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        ivSave.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.KEY_CARD_QUESTION, etQuestion.getText().toString());
            intent.putExtra(MainActivity.KEY_CARD_ANSWER, etAnswer.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}