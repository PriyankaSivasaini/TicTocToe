package com.example.win10.tictoctoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[][] buttons=new Button[3][3];
    private int player1points;
    private int player2points;
    private boolean player1turn;
    private int roundcount;
    private TextView tvplayer1;
    private TextView tvplayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvplayer1=findViewById(R.id.tv_player1);
        tvplayer2=findViewById(R.id.tv_player2);

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                String btnID="button_"+i+j;
                int resID=getResources().getIdentifier(btnID,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        player1turn=true;
        Button btnreset=findViewById(R.id.button_reset);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        if (player1turn){
            ((Button)v).setText("X");
        }
        else {
            ((Button)v).setText("O");
        }
        roundcount++;


        if (checkForWin()){
            if (player1turn){
                player1Wins();
            }else {
                player2Wins();
            }
        }
        else if (roundcount==9){
            draw();
        }else {
            player1turn=!player1turn;
        }
    }
    private boolean checkForWin(){
        String[][] field=new String[3][3];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){
            if (field[i][0].equals(field[i][1])
                    &&field[i][0].equals(field[i][2])
                    &&!field[i][0].equals("")){
                return true;
            }

        }
        for (int i=0;i<3;i++){
            if (field[0][i].equals(field[1][i])
                    &&field[0][i].equals(field[2][i])
                    &&!field[0][i].equals("")){
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                &&field[0][0].equals(field[2][2])
                &&!field[0][0].equals("")){
            return true;
        }
        if (field[0][2].equals(field[1][1])
                &&field[0][2].equals(field[2][0])
                &&!field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1points++;
        Toast.makeText(this, "Player1Wins", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();

    }
    private void player2Wins(){
        player2points++;
        Toast.makeText(this, "Player2Wins", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();


    }
    private void draw(){
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();

    }
    private void updatePoints(){
        tvplayer1.setText("Player1: "+player1points );
        tvplayer2.setText("Player2: "+player2points);
    }
    private void resetBoard(){
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundcount=0;
        player1turn=true;
    }
    private void resetGame(){
        player1points=0;
        player2points=0;
        updatePoints();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundcount",roundcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("playerturn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //savedInstanceState.putInt("roundcount",roundcount);
        roundcount=savedInstanceState.getInt("roundcount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1turn=savedInstanceState.getBoolean("playerturn");
    }
}
