// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Displays the running tally of bids, melds, and points.
 * 
 * @author Judson D Neer
 * @see android.app.Fragment
 */
public class StatusFragment extends Fragment {

    // References to view widgets. Having them in
    // arrays makes it easy to populate them later.
    private final TextView[] playerText = new TextView[4];
    private final TextView[][] scoreText = new TextView[4][2];
    private final TextView[] bidText = new TextView[4];
    private final TextView[] totalText = new TextView[2];

    /**
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Grab references to various view widgets. Unfortunately
        // Android doesn't make it easy to do this with loops.
        View resultView = inflater.inflate(R.layout.fragment_status, container, false);
        playerText[0] = (TextView)resultView.findViewById(R.id.player0_name);
        playerText[1] = (TextView)resultView.findViewById(R.id.player1_name);
        playerText[2] = (TextView)resultView.findViewById(R.id.player2_name);
        playerText[3] = (TextView)resultView.findViewById(R.id.player3_name);
        scoreText[0][0] = (TextView)resultView.findViewById(R.id.hand0_team0);
        scoreText[0][1] = (TextView)resultView.findViewById(R.id.hand0_team1);
        scoreText[1][0] = (TextView)resultView.findViewById(R.id.hand1_team0);
        scoreText[1][1] = (TextView)resultView.findViewById(R.id.hand1_team1);
        scoreText[2][0] = (TextView)resultView.findViewById(R.id.hand2_team0);
        scoreText[2][1] = (TextView)resultView.findViewById(R.id.hand2_team1);
        scoreText[3][0] = (TextView)resultView.findViewById(R.id.hand3_team0);
        scoreText[3][1] = (TextView)resultView.findViewById(R.id.hand3_team1);
        bidText[0] = (TextView)resultView.findViewById(R.id.hand0_bid);
        bidText[1] = (TextView)resultView.findViewById(R.id.hand1_bid);
        bidText[2] = (TextView)resultView.findViewById(R.id.hand2_bid);
        bidText[3] = (TextView)resultView.findViewById(R.id.hand3_bid);
        totalText[0] = (TextView)resultView.findViewById(R.id.total_team0);
        totalText[1] = (TextView)resultView.findViewById(R.id.total_team1);
        return resultView;
    }

    /**
     * Updates all the view widgets with game data.
     * 
     * @param game The object from which the view should be updated.
     */
    public void update(Game game) {
        // Set the player names. All these fields should be
        // bold, and also we'll italicize the dealer name.
        for (int p = 0; p < 4; p++) {
            playerText[p].setText(game.getPlayer(p));
            if (game.isDealer(p)) {
                playerText[p].setTypeface(null, Typeface.BOLD_ITALIC);
            }
            else {
                playerText[p].setTypeface(null, Typeface.BOLD);
            }
        }
        // For each hand and each team, set their respective score, as well
        // as the bid information. Make the text color red if it's negative.
        for (int h = 0; h < 4; h++) {
            for (int t = 0; t < 2; t++) {
                String score = game.getScore(h, t);
                scoreText[h][t].setText(score);
                if (score.length() > 0 && score.charAt(0) == '-') {
                    scoreText[h][t].setTextColor(Color.RED);
                }
                else {
                    scoreText[h][t].setTextColor(Color.BLACK);
                }

            }
            bidText[h].setText(game.getBid(h));
        }
        // Get the total score for each team, again setting it red if negative.
        for (int t = 0; t < 2; t++) {
            String total = game.getTotalStr(t);
            totalText[t].setText(total);
            if (total.length() > 0 && total.charAt(0) == '-') {
                totalText[t].setTextColor(Color.RED);
            }
            else {
                totalText[t].setTextColor(Color.BLACK);
            }
        }
    }

}
