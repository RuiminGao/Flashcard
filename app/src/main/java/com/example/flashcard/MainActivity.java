package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CARD_CODE = 20;

    public static final String KEY_CARD_QUESTION = "card_question";
    public static final String KEY_CARD_ANSWER = "card_answer";

    ImageView ivVisible, ivInvisible, ivEdit, ivAdd;
    TextView tvQuestion, tvAns;
    TextView[] tvOptions;
    RelativeLayout rlCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivVisible = findViewById(R.id.ivVisible);
        ivInvisible = findViewById(R.id.ivInvisible);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvAns = findViewById(R.id.tvAns);
        tvOptions = new TextView[]{findViewById(R.id.tvOption1), findViewById(R.id.tvOption2), findViewById(R.id.tvOption3)};
        int answerKey = 2;
        rlCard = findViewById(R.id.rlCard);
        ivEdit = findViewById(R.id.ivEdit);
        ivAdd = findViewById(R.id.ivAdd);

        ivEdit.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddCardActivity.class);
            i.putExtra(KEY_CARD_QUESTION, tvQuestion.getText().toString());
            i.putExtra(KEY_CARD_ANSWER, tvAns.getText().toString());
            startActivityForResult(i, ADD_CARD_CODE);
        });

        ivAdd.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddCardActivity.class);
            startActivityForResult(i, ADD_CARD_CODE);
        });

        rlCard.setOnClickListener(view -> {
            for (TextView tvOption : tvOptions) {
                tvOption.setBackgroundColor(getResources().getColor(R.color.grey));
                tvOption.setTextColor(getResources().getColor(R.color.grey2));
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
                    view.setBackgroundColor(getResources().getColor(R.color.green));
                    ((TextView) view).setTextColor(getResources().getColor(R.color.green2));
                } else {
                    tvOptions[answerKey].setBackgroundColor(getResources().getColor(R.color.green));
                    tvOptions[answerKey].setTextColor(getResources().getColor(R.color.green2));
                    view.setBackgroundColor(getResources().getColor(R.color.red));
                    ((TextView) view).setTextColor(getResources().getColor(R.color.red2));
                }

            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            assert data != null;
            tvQuestion.setText(data.getStringExtra(KEY_CARD_QUESTION));
            tvAns.setText(data.getStringExtra(KEY_CARD_ANSWER));
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