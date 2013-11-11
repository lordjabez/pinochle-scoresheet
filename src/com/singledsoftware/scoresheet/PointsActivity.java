// Copyright 2013 Judson D Neer

package com.singledsoftware.scoresheet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Provides interface for user to enter the points for a hand.
 * 
 * @author Judson D Neer
 * @see ScoresheetActivity
 */
public class PointsActivity extends ScoresheetActivity {

    // Points must be between these values. The default points were chosen
    // based on anecdotally typical point results in the Neer household.
    private static final int MINIMUM_POINTS = 0;
    private static final int MAXIMUM_POINTS = 25;
    private static final int DEFAULT_POINTS_BIDDER = 17;
    private static final int DEFAULT_POINTS_NONBIDDER = 8;
    
    // References to various view widgets.
    private TextView team0Text;
    private TextView team1Text;
    private TextView points0Text;
    private TextView points1Text;
    private Button points0UpButton;
    private Button points1UpButton;
    private Button points1DownButton;
    private Button points0DownButton;
    private StatusFragment statusFragment;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        // Grab references to various view widgets.
        team0Text = (TextView)this.findViewById(R.id.team0_text);
        team1Text = (TextView)this.findViewById(R.id.team1_text);
        points0Text = (TextView)this.findViewById(R.id.points0_text);
        points1Text = (TextView)this.findViewById(R.id.points1_text);
        points0UpButton = (Button)this.findViewById(R.id.points0_up_button);
        points1UpButton = (Button)this.findViewById(R.id.points1_up_button);
        points0DownButton = (Button)this.findViewById(R.id.points0_down_button);
        points1DownButton = (Button)this.findViewById(R.id.points1_down_button);
        statusFragment = (StatusFragment)getFragmentManager().findFragmentById(R.id.status_fragment);
        // Set the headers above the point adjustment buttons.
        team0Text.setText(game.getPlayer(0) + " & " + game.getPlayer(2));
        team1Text.setText(game.getPlayer(1) + " & " + game.getPlayer(3));
        // Set the point value indicators.
        if (game.bidderOnTeam(0)) {
            points0Text.setText(DEFAULT_POINTS_BIDDER + "");
            points1Text.setText(DEFAULT_POINTS_NONBIDDER + "");
        }
        else {
            points0Text.setText(DEFAULT_POINTS_NONBIDDER + "");
            points1Text.setText(DEFAULT_POINTS_BIDDER + "");
        }
        // Update the status widget with new game data.
        statusFragment.update(game);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_actionbar, menu);
        return true;
    }

    /**
     * Adjusts the point values up or down.
     * 
     * @param button The clicked button that called this method 
     */
    public void onClick(View button) {
        // Grab the relevant point values and adjust accordingly.
        int points0 = Integer.parseInt(points0Text.getText().toString());
        int points1 = Integer.parseInt(points1Text.getText().toString());
        // Note here that when one point value goes up, the other must go down.
        switch (button.getId()) {
            case R.id.points0_up_button: points0++; points1--; break;
            case R.id.points0_down_button: points0--; points1++; break;
            case R.id.points1_up_button: points1++; points0--; break;
            case R.id.points1_down_button: points1--; points0++; break;
        }
        // Ensure point values are within acceptable ranges,
        // and also disable the up/down buttons if needed.
        points0 = Math.max(points0, MINIMUM_POINTS);
        points0 = Math.min(points0, MAXIMUM_POINTS);
        points1 = Math.max(points1, MINIMUM_POINTS);
        points1 = Math.min(points1, MAXIMUM_POINTS);
        points0UpButton.setEnabled(points0 < MAXIMUM_POINTS);
        points0DownButton.setEnabled(points0 > MINIMUM_POINTS);
        points1UpButton.setEnabled(points1 < MAXIMUM_POINTS);
        points1DownButton.setEnabled(points1 > MINIMUM_POINTS);
        // Set the point indicators appropriately.
        points0Text.setText(points0 + "");
        points1Text.setText(points1 + "");
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
            case R.id.ok_action:
                // Save point data to the game object before proceeding.
                int points0 = Integer.parseInt(points0Text.getText().toString());
                int points1 = Integer.parseInt(points1Text.getText().toString());
                game.setPoints(points0, points1);
                // If we've reached the end of the game we go to the final
                // activity, otherwise we go to bidding on the next hand.
                Class<?> activityClass = game.isGameFinished() ? FinishedActivity.class : BidActivity.class;
                ScoresheetIntent intent = new ScoresheetIntent(this, activityClass, game);
                startActivity(intent);
                break;
        }
    }
    
}
