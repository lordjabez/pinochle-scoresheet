// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Displays final game results.
 * 
 * @author Judson D Neer
 * @see ScoresheetActivity
 */
public class FinishedActivity extends ScoresheetActivity {

    // References to various view widgets
    private TextView winningTeamText;
    private TextView defeatedText;
    private TextView losingTeamText;
    private TextView scoreText;
    private TextView finalScoreText;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        // Grab references to various view widgets.
        winningTeamText = (TextView)this.findViewById(R.id.winningTeam_text);
        defeatedText = (TextView)this.findViewById(R.id.defeated_text);
        losingTeamText = (TextView)this.findViewById(R.id.losingTeam_text);
        scoreText = (TextView)this.findViewById(R.id.score_text);
        finalScoreText = (TextView)this.findViewById(R.id.finalscore_text);
        // Populate team names, point totals, and the final score.
        String team0 = game.getPlayer(0) + " & " + game.getPlayer(2);
        String team1 = game.getPlayer(1) + " & " + game.getPlayer(3);
        int total0 = game.getTotal(0);
        int total1 = game.getTotal(1);
        String score = Math.max(total0, total1) + " to " + Math.min(total0, total1);
        int winningTeam = game.getWinningTeam();
        // Fill in the view widgets with the values from above. 
        finalScoreText.setText(score);
        if (winningTeam >= 0) {
            String winners = winningTeam == 0 ? team0 : team1;
            String losers = winningTeam == 0 ? team1 : team0;
            winningTeamText.setText(winners);
            losingTeamText.setText(losers);
            defeatedText.setText("defeated");
            scoreText.setText("by a score of");
        }
        else {
            winningTeamText.setText(team0);
            losingTeamText.setText(team1);
            defeatedText.setText("tied");
            scoreText.setText("with a score of");
        }
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finished_actionbar, menu);
        return true;
    }

    /**
     * Executes an action based on menu selection.
     * 
     * @param item The clicked menu item that called this method
     */
    public void takeAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo_action:
                this.finish();
                break;
            case R.id.new_action:
                ScoresheetIntent intent = new ScoresheetIntent(this, PlayersActivity.class, game);
                startActivity(intent);
                break;
        }
    }
    
}
