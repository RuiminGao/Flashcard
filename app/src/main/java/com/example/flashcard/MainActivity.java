package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CARD_CODE = 20;

    public static final String KEY_CARD_QUESTION = "card_question";
    public static final String KEY_CARD_ANSWER = "card_answer";
    public static final String[] KEY_CARD_OPTIONS = {"option_1","option_2","option_3"};
    public static final String KEY_ANSWER = "answer_key";

    ImageView ivVisible, ivInvisible, ivEdit, ivAdd;
    TextView tvQuestion, tvAns;
    TextView[] tvOptions;
    RelativeLayout rlCard;
    int answerKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivVisible = findViewById(R.id.ivVisible);
        ivInvisible = findViewById(R.id.ivInvisible);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvAns = findViewById(R.id.tvAns);
        tvOptions = new TextView[]{findViewById(R.id.tvOption1), findViewById(R.id.tvOption2), findViewById(R.id.tvOption3)};
        answerKey = 2;
        rlCard = findViewById(R.id.rlCard);
        ivEdit = findViewById(R.id.ivEdit);
        ivAdd = findViewById(R.id.ivAdd);

        ivEdit.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddCardActivity.class);
            i.putExtra(KEY_CARD_QUESTION, tvQuestion.getText().toString());
            i.putExtra(KEY_CARD_ANSWER, tvAns.getText().toString());
            for(int j = 0; j < tvOptions.length; j++) {
                i.putExtra(KEY_CARD_OPTIONS[j], tvOptions[j].getText().toString());
            }
            i.putExtra(KEY_ANSWER, answerKey);
            startActivityForResult(i, ADD_CARD_CODE);
        });

        ivAdd.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddCardActivity.class);
            startActivityForResult(i, ADD_CARD_CODE);
        });

        rlCard.setOnClickListener(view -> {
            for (TextView tvOption : tvOptions) {
                tvOption.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                tvOption.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey2));
            }
        });

        tvQuestion.setOnClickListener(view -> setToggle(view,tvAns));

        tvAns.setOnClickListener(view -> setToggle(view,tvQuestion));

        ivVisible.setOnClickListener(view -> {
            setToggle(ivVisible,ivInvisible);
            setOptions(View.VISIBLE);
        });

        ivInvisible.setOnClickListener(view -> {
            setToggle(ivInvisible,ivVisible);
            setOptions(View.INVISIBLE);
        });

        for (TextView tvOption : tvOptions) {
            tvOption.setOnClickListener(view -> {
                if (view == tvOptions[answerKey]) {
                    view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green2));
                } else {
                    tvOptions[answerKey].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    tvOptions[answerKey].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green2));
                    view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red2));
                }

            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            assert data != null;
            Toast.makeText(getApplicationContext(), "Saved.", Toast.LENGTH_SHORT).show();
            tvQuestion.setText(data.getStringExtra(KEY_CARD_QUESTION));
            tvAns.setText(data.getStringExtra(KEY_CARD_ANSWER));
            answerKey = data.getExtras().getInt(KEY_ANSWER)-1;
            for(int j = 0; j < 3; j++) {
                String text = data.getStringExtra(KEY_CARD_OPTIONS[j]);
                if (text.isEmpty()) {
                    tvOptions[j].setText("NULL");
                } else {
                    tvOptions[j].setText(text);
                }
            }
        }
    }

    private void setToggle(View v1, View v2) {
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.VISIBLE);
    }
    private void setOptions(int status) {
        for (TextView tvOption : tvOptions) {
            tvOption.setVisibility(status);
        }
    }
}