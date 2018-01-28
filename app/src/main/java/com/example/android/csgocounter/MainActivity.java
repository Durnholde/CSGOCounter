package com.example.android.csgocounter;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {
    int teamAMatchScore, teamBMatchScore = 0;
    int teamARoundScore, teamBRoundScore = 0;
    int format = 0;
    Spinner spinner_ct;
    Spinner spinner_t;

    String winner, loser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner_ct = (Spinner) findViewById(R.id.spinner_ct);
        spinner_t = (Spinner) findViewById(R.id.spinner_t);
    }

    /*
     * Logic methods.
     */
    boolean isMatchEnded() {
        if ((teamAMatchScore > format / 2) || (teamBMatchScore > format / 2))
            return true;
        else
            return false;
    }

    boolean isRoundEnded() {
        if ((teamARoundScore + teamBRoundScore <= 30) && (teamARoundScore == 16 || teamBRoundScore == 16))
            return true;
        else if (teamARoundScore + teamBRoundScore > 30) {
            int sum = teamARoundScore + teamBRoundScore - 30;
            while (sum > 6)
                sum -= 6;
            if (abs(teamARoundScore - teamBRoundScore) > 6 - sum)
                return true;
            else
                return false;
        } else
            return false;
    }

    /*
     * Methods for buttons.
     */
    void buttonTeamAPoint(View view) {
        if (!isRoundEnded() && !isMatchEnded()) {
            teamARoundScore += 1;
//            Spinner spinner_ct = (Spinner) findViewById(R.id.spinner_ct);
            winner = spinner_ct.getSelectedItem().toString();
            afterRound();
            displayTeamARoundScore(teamARoundScore);
        }

    }

    void buttonTeamBPoint(View view) {
        if (!isRoundEnded() && !isMatchEnded()) {
            teamBRoundScore += 1;
 //           Spinner spinner_t = (Spinner) findViewById(R.id.spinner_t);
            winner = spinner_t.getSelectedItem().toString();
            afterRound();
            displayTeamBRoundScore(teamBRoundScore);
        }
    }

    void reset(View view) {
        teamARoundScore = 0;
        teamBRoundScore = 0;
        teamAMatchScore = 0;
        teamBMatchScore = 0;
        displayMatchScore();
        displayTeamARoundScore(teamARoundScore);
        displayTeamBRoundScore(teamBRoundScore);
        displayMessage("Match about to start!");
    }

    void roundSummary(View view) {
        if (isRoundEnded()) {
            if (teamARoundScore > teamBRoundScore) {
                teamAMatchScore += 1;
//                Spinner spinner_ct = (Spinner) findViewById(R.id.spinner_ct);
//                Spinner spinner_t = (Spinner) findViewById(R.id.spinner_t);
                winner = spinner_ct.getSelectedItem().toString();
                loser = spinner_t.getSelectedItem().toString();
                afterMap();
            }
            else {
                teamBMatchScore += 1;
//                Spinner spinner_ct = (Spinner) findViewById(R.id.spinner_ct);
//                Spinner spinner_t = (Spinner) findViewById(R.id.spinner_t);
                winner = spinner_t.getSelectedItem().toString();
                loser = spinner_ct.getSelectedItem().toString();
                afterMap();
            }
            teamARoundScore = 0;
            teamBRoundScore = 0;
            displayTeamARoundScore(teamARoundScore);
            displayTeamBRoundScore(teamBRoundScore);
            displayMatchScore();

            if(isMatchEnded())
                afterMatch();
        }
    }

    void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.button_bo1:
                if (checked)
                    format = 1;
                break;
            case R.id.button_bo3:
                if (checked)
                    format = 3;
                break;
            case R.id.button_bo5:
                if (checked)
                    format = 5;
                break;
        }
        displayFormat();
        reset(view);
    }

    /*
     * Methods that display format, score on screen.
     */
    void displayTeamARoundScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_round_score);
        scoreView.setText(String.valueOf(score));
    }

    void displayTeamBRoundScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_round_score);
        scoreView.setText(String.valueOf(score));
    }

    void displayMatchScore() {
        TextView scoreView = (TextView) findViewById(R.id.match_score);
        String score = "" + teamAMatchScore + ":" + teamBMatchScore;
        scoreView.setText(score);
    }

    void displayFormat()
    {
        TextView formatView = (TextView) findViewById(R.id.format);
        String formatText = "( BO" + format + " )";
        formatView.setText(formatText);
    }

    /*
     * Message methods
     */
    void afterRound()
    {
        String message = winner + " has won the round!";
        displayMessage(message);
    }

    void afterMap()
    {
        String message = winner + " has won the map!";
        displayMessage(message);
    }

    void afterMatch()
    {
        String message = winner + " has defeted " + loser + "!";
        displayMessage(message);
    }

    void displayMessage(String message)
    {
        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText(message);
    }
}