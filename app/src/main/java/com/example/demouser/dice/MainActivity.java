package com.example.demouser.dice;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userScore = 0;
    private int compScore = 0;
    private int userTurnScore = 0;
    private int compTurnScore = 0;
    private ImageView diceView;
    private final Drawable[] dice = new Drawable[6];

    private TextView userScoreTV;
    private TextView computerScoreTV;
    private TextView status;


    private Button rollButton;
    private Button holdButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceView = findViewById(R.id.dice1);
        rollButton = findViewById(R.id.roll);
        holdButton = findViewById(R.id.hold);
        resetButton = findViewById(R.id.reset);
        status = findViewById(R.id.status);
        userScoreTV = findViewById(R.id.userTotalScore);
        computerScoreTV = findViewById(R.id.compTotalScore);

        Resources res = getResources();

        // dice pictures
        dice[0] = ContextCompat.getDrawable(this, R.drawable.dice1);
        dice[1] = ContextCompat.getDrawable(this, R.drawable.dice2);
        dice[2] = ContextCompat.getDrawable(this, R.drawable.dice3);
        dice[3] = ContextCompat.getDrawable(this, R.drawable.dice4);
        dice[4] = ContextCompat.getDrawable(this, R.drawable.dice5);
        dice[5] = ContextCompat.getDrawable(this, R.drawable.dice6);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = roller();
                if(result != 1) {
                    userTurnScore += result;
                    status.setText("Your turn score: " + Integer.toString(userTurnScore));
                }
                else {
                    status.setText("You roll 1! Now it's computer's turn!");
                    userTurnScore = 0;
                    computerTurn();
                }
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userScore += userTurnScore;
                userTurnScore = 0;
                status.setText("It's computer's turn!");
                refresh();
                computerTurn();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userScore = 0;
                compScore = 0;
                userTurnScore = 0;
                compTurnScore = 0;
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
                status.setText("New game!");
                refresh();
            }
        });
    }

    public int roller () {
        Random r = new Random();
        int index = r.nextInt(6); // generate a number between 0 and 5
        diceView.setImageDrawable(dice[index]);
        return index + 1;
    }

    public void refresh() {
        userScoreTV.setText(Integer.toString(userScore));
        computerScoreTV.setText(Integer.toString(compScore));
    }

    public void computerTurn() {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int result = roller();

                if(compTurnScore < 20 && result != 1) {
                    compTurnScore += result;
                    status.setText("Computer round score: " + Integer.toString(compTurnScore));
                    handler.postDelayed(this, 2000);
                }
                else if(result == 1) {
                    status.setText("Computer rolls 1! Now it's your turn!");
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }
                else {
                    compScore += compTurnScore;
                    computerScoreTV.setText(Integer.toString(compScore));
                    status.setText("It's your turn!");
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }
//                refresh();
            }
        };
        handler.postDelayed(r, 2000);
        compTurnScore = 0;
        refresh();

    }
}
