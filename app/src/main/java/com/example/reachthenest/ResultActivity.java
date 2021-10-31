package com.example.reachthenest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewResultInfo,textViewMyScore,textViewHighestScore;
    private Button buttonAgain;
    private  int score;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewHighestScore = findViewById(R.id.textViewHighestScore);
        textViewMyScore = findViewById(R.id.textViewMyScore);
        textViewResultInfo = findViewById(R.id.textViewResultInfo);
        buttonAgain = findViewById(R.id.buttonAgain);

        score = getIntent().getIntExtra("score",0);
        textViewMyScore.setText("Your Score : "+score);

        sharedPreferences = this.getSharedPreferences("score",MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highestScore",0);

        if (score>=500)
        {
            textViewResultInfo.setText("You Won The Game");
            textViewHighestScore.setText("Highest Score : "+score);
            sharedPreferences.edit().putInt("highestScore",score).apply();
        }

        else if (score >= highestScore)
        {
            textViewResultInfo.setText("Sorry, You Lost The Game.");
            textViewHighestScore.setText("Highest Score : "+score);
            sharedPreferences.edit().putInt("highestScore",score).apply();
        }
        else
        {
            textViewResultInfo.setText("Sorry, You Lost The Game.");
            textViewHighestScore.setText("Highest Score : "+highestScore);
        }

        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle("Help This Bird To Reach The Nest");
        builder.setMessage("Are You Sure You Want To Quit The Game? ");
        builder.setCancelable(false);
        builder.setNegativeButton("Quit Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();

    }
}