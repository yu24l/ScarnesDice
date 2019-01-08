package com.example.demouser.dice;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
    private boolean compTurn = false;
    private ImageView diceView;
    private final Drawable[] dice = new Drawable[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceView = findViewById(R.id.dice1);
        final Button rollButton = findViewById(R.id.roll);
        final Button holdButton = findViewById(R.id.hold);
        final Button resetButton = findViewById(R.id.reset);
        final TextView status = findViewById(R.id.status);
        final TextView userScoreTV = findViewById(R.id.userTotalScore);
        final TextView computerScoreTV = findViewById(R.id.compTotalScore);

        Resources res = getResources();

        int face;
        // dice pictures
        dice[0] = ContextCompat.getDrawable(this, R.drawable.dice1);
        dice[1] = ContextCompat.getDrawable(this, R.drawable.dice2);
        dice[2] = ContextCompat.getDrawable(this, R.drawable.dice3);
        dice[3] = ContextCompat.getDrawable(this, R.drawable.dice4);
        dice[4] = ContextCompat.getDrawable(this, R.drawable.dice5);
        dice[5] = ContextCompat.getDrawable(this, R.drawable.dice6);




//        diceView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                tv.setTextSize(tv.getTextSize()+5);
//            }
//        });

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = roller();
                if(result != 1) {
                    if(!compTurn){
                        userTurnScore += result;
                        status.setText("Your turn score: " + Integer.toString(userTurnScore));
                    }
                    else {
                        compTurnScore += result;
                        status.setText("Computer round score: " + Integer.toString(compTurnScore));
                    }
                }
                else {
                    if (!compTurn) {
                        status.setText("You roll 1!");
                        userTurnScore = 0;
                        compTurn = true;
                    } else {
                        status.setText("Computer rolls 1!");
                        compTurnScore = 0;
                        compTurn = false;
                    }
                }
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compTurn){
                    userScore += userTurnScore;
                    userTurnScore = 0;
                    userScoreTV.setText(Integer.toString(userScore));
                    compTurn = true;
//                    rollButton.setEnabled(false);
                }
                else{
                    compScore += compTurnScore;
                    compTurnScore = 0;
                    computerScoreTV.setText(Integer.toString(compScore));
                    compTurn = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userScore = 0;
                compScore = 0;
                userTurnScore = 0;
                compTurnScore = 0;

            }
        });
    }

    public int roller (){
        Random r = new Random();
        int index = r.nextInt(5); // generate a number between 0 and 5
        diceView.setImageDrawable(dice[index]);
        return index + 1;
    }

}
