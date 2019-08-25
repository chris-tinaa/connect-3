package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean gameActive = true;

    // 0 = yellow, 1 = red

    int activePlayer = 0;

    // 2 = unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tag = Integer.parseInt(counter.getTag().toString());

        if (gameState[tag] == 2 && gameActive) {

            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                gameState[tag] = 0;
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                gameState[tag] = 1;
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f);


            for (int[] winningPosition : winningPositions) {
                String winner = "";
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[0]] == gameState[winningPosition[2]]) {
                    if (gameState[winningPosition[0]] != 2) {

                        //someone won
                        gameActive = false;

                        if (gameState[winningPosition[0]] == 0){
                            winner = "Yellow has won!";
                        } else if (gameState[winningPosition[0]] == 1) {
                            winner = "Red has won!";
                        }

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText(winner);

                        LinearLayout layout = (LinearLayout)findViewById(R.id.gameOver);
                        layout.setVisibility(View.VISIBLE);
                    }
                } else {
                    int i = 0;
                    boolean full = true;
                    while (full && i < gameState.length){
                        full = (gameState[i] != 2);
                        i++;
                    }

                    if (full) {
                        gameActive = false;

                        winner = "Draw!";

                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText(winner);

                        LinearLayout layout = (LinearLayout) findViewById(R.id.gameOver);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


    public void tryAgain(View view){
        LinearLayout layout = (LinearLayout)findViewById(R.id.gameOver);
        layout.setVisibility(View.INVISIBLE);

        gameActive = true;
        activePlayer = 1;

        for (int i = 0 ; i < gameState.length ; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount() ; i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
